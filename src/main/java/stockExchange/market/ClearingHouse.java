package stockExchange.market;

import stockExchange.dataStructure.binomialHashMap.BinomialHashMap;
import stockExchange.dataStructure.binomialHeap.BuyBinomialMaxHeap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class ClearingHouse {
    private final OrderReader buyReader;
    private final OrderReader sellReader;
    private BinomialHashMap pendingSellOrders;
    private BuyBinomialMaxHeap pendingBuyOrders;

    public ClearingHouse() throws FileNotFoundException {
        this.buyReader = new OrderReader("SortedBuyerDataFile1.csv");
        this.sellReader = new OrderReader("SortedSellerDataFile1.csv");
    }

    public BuyBinomialMaxHeap fetchBuyOrders() throws IOException {
        long startTime = buyReader.getTime();
        BuyBinomialMaxHeap buyOrders = new BuyBinomialMaxHeap();
        while (buyReader.getTime() == startTime) {
            Order order = buyReader.getOrder();
            buyOrders.insert(order);
        }
        if (pendingBuyOrders != null && pendingBuyOrders.getSize() != 0) {
            buyOrders.merge(pendingBuyOrders.getRoot());
        }
        return buyOrders;
    }

    public BinomialHashMap fetchSellOrders() throws IOException {
        long startTime = sellReader.getTime();
        BinomialHashMap sellOrders = new BinomialHashMap();
        while (sellReader.getTime() == startTime) {
            Order order = sellReader.getOrder();
            sellOrders.insert(order);
        }
        if (pendingSellOrders != null) {
            sellOrders.merge(pendingSellOrders);
        }
        return sellOrders;
    }

    public void performTransactions(BuyBinomialMaxHeap buyOrders, BinomialHashMap sellOrders) {
        pendingBuyOrders = new BuyBinomialMaxHeap();
        pendingSellOrders = new BinomialHashMap();
        long buyOrdersCompleted = 0;
        long buyOrdersPartiallyCompleted = 0;
        while (!buyOrders.isEmpty()) {
            Order order = buyOrders.extractHighestPriorityElement().getKey();
            Long quantityFulfilled = sellOrders.fetchOrder(order.getPrice(), order.getQuantity());
            if (quantityFulfilled != 0) {
                if (quantityFulfilled == order.getQuantity()) {
                    buyOrdersCompleted++;
                } else {
                    long remainingQuantity = order.getQuantity() - quantityFulfilled;
                    order.setQuantity(remainingQuantity);
                    pendingBuyOrders.insert(order);
                    buyOrdersPartiallyCompleted++;
                }
            } else {
                //insert the node in the new binomial tree
                pendingBuyOrders.insert(order);
            }
        }
        pendingSellOrders = sellOrders;
        System.out.println("Buy orders completed: " + buyOrdersCompleted);
        System.out.println("Buy orders partially completed: " + buyOrdersPartiallyCompleted);
        System.out.println("Sell orders completed: " + sellOrders.ordersCompleted);
        System.out.println("Sell orders partially processed: " + buyOrdersPartiallyCompleted);
        System.out.println();
    }

    public boolean isEOD() throws IOException {
        return buyReader.getTime() == -1 || sellReader.getTime() == -1;
    }

    public Date getCurrentTime() throws IOException {
        long currentTime = buyReader.getTime();
        if (currentTime == -1) {
            return null;
        }
        return new Date(currentTime * 1000);
    }
}
