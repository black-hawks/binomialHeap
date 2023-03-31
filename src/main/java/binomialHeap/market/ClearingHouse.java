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
    private BinomialHashMap sellOrdersPostTransaction;
    private BuyBinomialMaxHeap buyOrdersPostTransaction;

    public ClearingHouse() throws FileNotFoundException {
        this.buyOrders = new BuyBinomialMaxHeap();
        this.sellOrders = new BinomialHashMap();
        this.buyReader = new OrderReader("buyOrder.csv", Duration.ofSeconds(1));
        this.sellReader = new OrderReader("sellOrder.csv", Duration.ofSeconds(1));
    }

    public void initMarket() throws IOException, ParseException {
        while(true){
            fetchOrders();  
        }
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
        if(buyOrdersPostTransaction != null){

            buyOrders.merge(buyOrdersPostTransaction.getRoot());
        }
        if(sellOrdersPostTransaction != null){
            sellOrders.merge(sellOrders);
        }
        performTransactions();

    }

    private void performTransactions() {
        buyOrdersPostTransaction = new BuyBinomialMaxHeap();
        sellOrdersPostTransaction = new BinomialHashMap();
        while(!buyOrders.isEmpty()){
            Order order = buyOrders.extractHighestPriorityElement().getKey();
            Long quantity = sellOrders.fetchOrder(order.getPrice(),order.getQuantity());
            if( quantity != null){
                if(quantity == 0){
                    buyOrders.extractHighestPriorityElement();
                }else{
                    order.setQuantity(quantity);
                    buyOrdersPostTransaction.insert(order);
                }
               
            }else{
                 //insert the node inot the new binomial tree
                 buyOrdersPostTransaction.insert(order);
            }

        }
        sellOrdersPostTransaction = sellOrders;
    }
}
