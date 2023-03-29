package binomialHeap.dataStructure.binomialHeap;

import java.util.HashMap;

public class BinomialHashMap {
    private HashMap<Double, BuyBinomialMaxHeap> binomialHashMap;

    public BinomialHashMap() {
        this.binomialHashMap = new HashMap<>();
    }

    public void insert(Order order) {
        if (binomialHashMap.containsKey(order.price)) {
            BuyBinomialMaxHeap binomialHeap = binomialHashMap.get(order.price);
            binomialHeap.insert(order);
        } else {
            BuyBinomialMaxHeap binomialHeap = new BuyBinomialMaxHeap();
            binomialHeap.insert(order);
            binomialHashMap.put(order.price, binomialHeap);
        }
    }

    public Order fetchOrder(double price, long quantity) {
        if(!binomialHashMap.containsKey(price)) {
            return null;
        }
        BuyBinomialMaxHeap binomialHeap = binomialHashMap.get(price);
        Order order = binomialHeap.peekHighestOrder();
        if(order.quantity == quantity) {
            binomialHeap.extractHighestPriorityElement();
        } else if (order.quantity < quantity) {

        } else {

        }

    }
}
