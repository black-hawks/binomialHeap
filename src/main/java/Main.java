import stockExchange.dataStructure.binomialHashMap.BinomialHashMap;
import stockExchange.dataStructure.binomialHeap.BuyBinomialMaxHeap;
import stockExchange.market.ClearingHouse;

import java.io.IOException;

/**
 * Simulates the Whole Project To generate Binomial Heap
 */
public class Main {

    /**
     * Creates an instance of ClearingHouse and fetches buy and sell orders, then performs transactions between them
     * until the end of the day (EOD).
     *
     * @param args command line arguments, not used in this implementation.
     * @throws IOException if there is an error reading input or output files.
     */
    public static void main(String[] args) throws IOException {
        ClearingHouse clearingHouse = new ClearingHouse();
        while (!clearingHouse.isEOD()) {
            BuyBinomialMaxHeap buyOrders = clearingHouse.fetchBuyOrders();
            BinomialHashMap sellOrders = clearingHouse.fetchSellOrders();
            clearingHouse.performTransactions(buyOrders, sellOrders);
        }
    }
}
