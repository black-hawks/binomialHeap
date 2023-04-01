import stockExchange.dataStructure.binomialHashMap.BinomialHashMap;
import stockExchange.dataStructure.binomialHeap.BuyBinomialMaxHeap;
import stockExchange.market.ClearingHouse;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClearingHouse clearingHouse = new ClearingHouse();
        while (!clearingHouse.isEOD()) {
            BuyBinomialMaxHeap buyOrders = clearingHouse.fetchBuyOrders();
            BinomialHashMap sellOrders = clearingHouse.fetchSellOrders();
            clearingHouse.performTransactions(buyOrders, sellOrders);
        }
    }
}
