package binomialHeap.dataStructure.binomialHeap;

import java.util.HashMap;
import java.util.Map;

public class BinomialHashMap {
    private HashMap<Double, SellBinomialMinHeap> binomialHashMap;

    public BinomialHashMap() {
        this.binomialHashMap = new HashMap<>();
    }

    public void insert(Order order) {
        if (binomialHashMap.containsKey(order.price)) {
            SellBinomialMinHeap binomialHeap = binomialHashMap.get(order.price);
            binomialHeap.insert(order);
        } else {
            SellBinomialMinHeap binomialHeap = new SellBinomialMinHeap();
            binomialHeap.insert(order);
            binomialHashMap.put(order.price, binomialHeap);
        }
    }

    public Long fetchOrder(double price, long quantity) {
        long quantityFulfilled = 0;
        if (!binomialHashMap.containsKey(price)) {
            return quantityFulfilled;
        }
        SellBinomialMinHeap binomialHeap = binomialHashMap.get(price);
        //checking if the binominal tree is empty
        if (binomialHeap.isEmpty()) {
            return quantityFulfilled;
        }
        Order order = binomialHeap.peekHighestOrder();
        if (order.quantity == quantity) {
            quantityFulfilled = quantity;
            binomialHeap.extractHighestPriorityElement();
        } else if (order.quantity < quantity) {
//            quantity = quantity - order.quantity;
            quantityFulfilled = order.quantity;
            binomialHeap.extractHighestPriorityElement();
            quantityFulfilled += fetchOrder(price, quantity - quantityFulfilled);
        } else {
            order.quantity = order.quantity - quantity;
            quantityFulfilled = quantity;// this means that we have processed the quantity of the buy shares
        }
        if (binomialHeap.isEmpty()) {
            binomialHashMap.remove(price);
        }
        return quantityFulfilled;

    }

    public void merge(BinomialHashMap newBinomialHashMap) {
        for (Map.Entry<Double, SellBinomialMinHeap> entry : newBinomialHashMap.getBinomialHashMap().entrySet()) {
            Double key = entry.getKey();
            SellBinomialMinHeap value = entry.getValue();

            if (this.binomialHashMap.containsKey(key)) {
                // Handle key conflict by merging the values
                SellBinomialMinHeap existingValue = this.binomialHashMap.get(key);
                existingValue.merge(value.getRoot());
                this.binomialHashMap.put(key, existingValue);
            } else {
                // No key conflict, add the key-value pair to map1
                this.binomialHashMap.put(key, value);
            }
        }
    }

    public HashMap<Double, SellBinomialMinHeap> getBinomialHashMap() {
        return binomialHashMap;
    }
}
