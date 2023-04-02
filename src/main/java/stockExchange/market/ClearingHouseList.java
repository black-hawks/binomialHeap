package stockExchange.market;

import stockExchange.dataStructure.binomialHeap.BinomialHeapNode;
import stockExchange.dataStructure.binomialHeap.Prioritizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class ClearingHouseList {
    private final OrderReader buyReader;
    private final OrderReader sellReader;
    private PriorityQueue<Order> pendingSellOrders;
    private PriorityQueue<Order> pendingBuyOrders;

    public ClearingHouseList() throws FileNotFoundException {
        this.buyReader = new OrderReader("SortedBuyerDataFile1.csv");
        this.sellReader = new OrderReader("SortedSellerDataFile1.csv");
    }

    public void initMarket() throws IOException, ParseException {
        while (!isEOD()) {
            PriorityQueue<Order> buyOrders = fetchBuyOrders();
            PriorityQueue<Order> sellOrders = fetchSellOrders();
            performTransactions(buyOrders, sellOrders);
        }
    }

    public PriorityQueue<Order> fetchBuyOrders() throws IOException {
        long startTime = buyReader.getTime();
        PriorityQueue<Order> buyOrders = new PriorityQueue<>(buyerComparator);
        while (buyReader.getTime() == startTime) {
            Order order = buyReader.getOrder();
            buyOrders.offer(order);
        }
        if (pendingBuyOrders != null && pendingBuyOrders.size() != 0) {
            buyOrders.addAll(pendingBuyOrders);
        }
        return buyOrders;
    }

    public PriorityQueue<Order> fetchSellOrders() throws IOException, ParseException {
        long startTime = sellReader.getTime();
        PriorityQueue<Order> sellOrders = new PriorityQueue<>(sellerComparator);
        while (sellReader.getTime() == startTime) {
            Order order = sellReader.getOrder();
            sellOrders.offer(order);
        }
        if (pendingSellOrders != null) {
            sellOrders.addAll(pendingSellOrders);
        }
        return sellOrders;
    }

    public void performTransactions(PriorityQueue<Order> buyOrders, PriorityQueue<Order> sellOrders) {
        pendingBuyOrders = new PriorityQueue<>(buyerComparator);
        pendingSellOrders = new PriorityQueue<>(sellerComparator);
        while (!buyOrders.isEmpty()) {
            Order order = buyOrders.poll();
            long quantityFulfilled = fetchOrder(sellOrders, order.getPrice(), order.getQuantity());
            if (quantityFulfilled != 0) {
                if (quantityFulfilled == order.getQuantity()) {
//                    buyOrders.remove(order);
                    System.out.println(order + " fulfilled");
                } else {
                    long remainingQuantity = order.getQuantity() - quantityFulfilled;
                    order.setQuantity(remainingQuantity);
                    pendingBuyOrders.offer(order);
                    System.out.println(order + " partially fulfilled, remaining quantity: " + remainingQuantity);
                }
            } else {
                //insert the node in the new binomial tree
                pendingBuyOrders.offer(order);
            }
        }
        pendingSellOrders = sellOrders;
    }

//    private boolean isEOD() throws IOException, ParseException {
//        return buyReader.getTime() == -1 || sellReader.getTime() == -1;
//    }

    public boolean isEOD() throws IOException {
        return buyReader.getTime() == -1 || sellReader.getTime() == -1;
    }

    private long fetchOrder(PriorityQueue<Order> sellOrders, double price, long quantity) {
        // create a temporary queue to hold orders with matching price
        PriorityQueue<Order> tempQueue = new PriorityQueue<>(sellOrders.comparator());

        // extract orders with matching price into the temporary queue
        for (Order order : sellOrders) {
            if (order.getPrice() == price) {
                tempQueue.offer(order);
            }
        }

        long quantityFulfilled = 0;
        // iterate through orders with matching price
        while (!tempQueue.isEmpty()) {
            Order order = tempQueue.peek();

            if (order.getQuantity() == quantity) {
                // order quantity matches requested quantity, remove order
                tempQueue.poll();
                quantityFulfilled += quantity;
                break;
            } else if (order.getQuantity() < quantity) {
                // order quantity is less than requested quantity, update order and fetch another order with same price
                quantity -= order.getQuantity();
                quantityFulfilled += order.getQuantity();
                tempQueue.poll();
            } else {
                // order quantity is greater than requested quantity, update order and return order ID
                order.setQuantity(order.getQuantity() - quantity);
                quantityFulfilled += quantity;
                break;
            }
        }
        sellOrders.addAll(tempQueue);
        // no matching order found
        return quantityFulfilled;
    }

    public final static Comparator<Order> buyerComparator = (a, b) -> {
        if (Prioritizer.maxHeapPrioritizer(new BinomialHeapNode(a), new BinomialHeapNode(b))) {
            return 1;
        }
        return -1;
    };

    public final static Comparator<Order> sellerComparator = (a, b) -> {
        if (Prioritizer.minHeapPrioritizer(new BinomialHeapNode(a), new BinomialHeapNode(b))) {
            return 1;
        }
        return -1;
    };

    public Date getCurrentTime() throws IOException {
        long currentTime = buyReader.getTime();
        if (currentTime == -1) {
            return null;
        }
        return new Date(currentTime * 1000);
    }
}
