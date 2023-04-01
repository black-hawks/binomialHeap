package binomialHeap.market;

import binomialHeap.dataStructure.binomialHeap.BinomialHashMap;
import binomialHeap.dataStructure.binomialHeap.BuyBinomialMaxHeap;
import binomialHeap.dataStructure.binomialHeap.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;

public class ClearingHouse {
    private BuyBinomialMaxHeap buyOrders;
    private BinomialHashMap sellOrders;
    private final OrderReader buyReader;
    private final OrderReader sellReader;
    private BinomialHashMap sellOrdersPostTransaction;
    private BuyBinomialMaxHeap buyOrdersPostTransaction;

    public ClearingHouse() throws FileNotFoundException {
        this.buyReader = new OrderReader("SortedBuyerDataFile1.csv", Duration.ofSeconds(1));
        this.sellReader = new OrderReader("SortedSellerDataFile1.csv", Duration.ofSeconds(1));
    }

    public void initMarket() throws IOException, ParseException {
        while (true) {
            fetchOrders();
            performTransactions();
        }
    }

    private void fetchOrders() throws IOException, ParseException {
        long startTime = buyReader.getTime();
        buyOrders = new BuyBinomialMaxHeap();
        sellOrders = new BinomialHashMap();
        while (buyReader.getTime() == startTime) {
            Order order = buyReader.getOrder();
            buyOrders.insert(order);
        }
        while (sellReader.getTime() == startTime) {
            Order order = sellReader.getOrder();
            sellOrders.insert(order);
        }
        if (buyOrdersPostTransaction != null && buyOrdersPostTransaction.getSize() != 0) {
            buyOrders.merge(buyOrdersPostTransaction.getRoot());
        }
        if (sellOrdersPostTransaction != null) {
            sellOrders.merge(sellOrdersPostTransaction);
        }
    }

    private void performTransactions() {
        buyOrdersPostTransaction = new BuyBinomialMaxHeap();
        sellOrdersPostTransaction = new BinomialHashMap();
        while (!buyOrders.isEmpty()) {
            Order order = buyOrders.extractHighestPriorityElement().getKey();
            Long quantityFulfilled = sellOrders.fetchOrder(order.getPrice(), order.getQuantity());
            if (quantityFulfilled != 0) {
                if (quantityFulfilled == order.getQuantity()) {
                    buyOrders.extractHighestPriorityElement();
                    System.out.println(order + " fulfilled");
                } else {
                    long remainingQuantity = order.getQuantity() - quantityFulfilled;
                    order.setQuantity(remainingQuantity);
                    buyOrdersPostTransaction.insert(order);
                    System.out.println(order + " partially fulfilled, remaining quantity: " + remainingQuantity);
                }

            } else {
                //insert the node inot the new binomial tree
                buyOrdersPostTransaction.insert(order);
//                System.out.println(order + " not fulfilled");
            }

        }
        sellOrdersPostTransaction = sellOrders;
    }
}
