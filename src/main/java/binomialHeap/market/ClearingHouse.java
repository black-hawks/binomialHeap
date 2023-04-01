package binomialHeap.market;

import binomialHeap.dataStructure.binomialHeap.BinomialHashMap;
import binomialHeap.dataStructure.binomialHeap.BuyBinomialMaxHeap;
import binomialHeap.dataStructure.binomialHeap.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;

public class ClearingHouse {
    private final OrderReader buyReader;
    private final OrderReader sellReader;
    private BinomialHashMap pendingSellOrders;
    private BuyBinomialMaxHeap pendingBuyOrders;

    public ClearingHouse() throws FileNotFoundException {
        this.buyReader = new OrderReader("SortedBuyerDataFile1.csv", Duration.ofSeconds(1));
        this.sellReader = new OrderReader("SortedSellerDataFile1.csv", Duration.ofSeconds(1));
    }

    public void initMarket() throws IOException, ParseException {
        while (!isEOD()) {
            BuyBinomialMaxHeap buyOrders = fetchBuyOrders();
            BinomialHashMap sellOrders = fetchSellOrders();
            performTransactions(buyOrders, sellOrders);
        }
    }

    private BuyBinomialMaxHeap fetchBuyOrders() throws IOException, ParseException {
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

    private BinomialHashMap fetchSellOrders() throws IOException, ParseException {
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

    private void performTransactions(BuyBinomialMaxHeap buyOrders, BinomialHashMap sellOrders) {
        pendingBuyOrders = new BuyBinomialMaxHeap();
        pendingSellOrders = new BinomialHashMap();
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
                    pendingBuyOrders.insert(order);
                    System.out.println(order + " partially fulfilled, remaining quantity: " + remainingQuantity);
                }
            } else {
                //insert the node in the new binomial tree
                pendingBuyOrders.insert(order);
            }
        }
        pendingSellOrders = sellOrders;
    }

    private boolean isEOD() throws IOException, ParseException {
        return buyReader.getTime() == -1 || sellReader.getTime() == -1;
    }
}
