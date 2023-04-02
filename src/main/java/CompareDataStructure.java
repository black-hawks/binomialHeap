import stockExchange.dataStructure.binomialHeap.BuyBinomialMaxHeap;
import stockExchange.market.ClearingHouse;
import stockExchange.market.ClearingHouseList;
import stockExchange.market.Order;

import java.io.IOException;
import java.util.PriorityQueue;

public class CompareDataStructure {

    public static void main(String[] args) throws IOException {
        mergeHeap();
        mergeQueue();
    }

    private static void mergeHeap() throws IOException {
        ClearingHouse clearingHouse = new ClearingHouse();
        while (!clearingHouse.isEOD()) {
            BuyBinomialMaxHeap buyOrders1 = clearingHouse.fetchBuyOrders();
            BuyBinomialMaxHeap buyOrders2 = clearingHouse.fetchBuyOrders();

            long start = System.nanoTime();
            buyOrders1.merge(buyOrders2.getRoot());
            System.out.println("Time took to merge heap: " + (System.nanoTime() - start) + " ns");
        }
    }

    private static void mergeQueue() throws IOException {
        ClearingHouseList clearingHouse = new ClearingHouseList();
        while (!clearingHouse.isEOD()) {
            PriorityQueue<Order> buyOrders1 = clearingHouse.fetchBuyOrders();
            PriorityQueue<Order> buyOrders2 = clearingHouse.fetchBuyOrders();

            long start = System.nanoTime();
            buyOrders1.addAll(buyOrders2);
            System.out.println("Time took to merge queue: " + (System.nanoTime() - start) + " ns");
        }
    }
}
