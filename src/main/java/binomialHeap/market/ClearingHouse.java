package binomialHeap.market;

import binomialHeap.dataStructure.binomialHeap.BinomialHashMap;
import binomialHeap.dataStructure.binomialHeap.BinomialHeapNode;
import binomialHeap.dataStructure.binomialHeap.BuyBinomialMaxHeap;
import binomialHeap.dataStructure.binomialHeap.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;

public class ClearingHouse {
    private final BuyBinomialMaxHeap buyOrders;
    private final BinomialHashMap sellOrders;
    private final OrderReader buyReader;
    private final OrderReader sellReader;

    public ClearingHouse() throws FileNotFoundException {
        this.buyOrders = new BuyBinomialMaxHeap();
        this.sellOrders = new BinomialHashMap();
        this.buyReader = new OrderReader("buyOrder.csv", Duration.ofSeconds(1));
        this.sellReader = new OrderReader("sellOrder.csv", Duration.ofSeconds(1));
    }

    public void initMarket() throws IOException, ParseException {
        fetchOrders();

    }

    private void fetchOrders() throws IOException, ParseException {
        long startTime = buyReader.getTime() / 1000;
        while (buyReader.getTime() / 1000 == startTime) {
            Order order = buyReader.getOrder();
            buyOrders.insert(order);
        }
        while (sellReader.getTime() / 1000 == startTime) {
            Order order = sellReader.getOrder();
            sellOrders.insert(order);
        }
    }

    private void performTransactions() {
        while(!buyOrders.isEmpty()){
            Order order = buyOrders.extractHighestPriorityElement().getKey();

        }
    }
}
