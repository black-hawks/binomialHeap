package stockExchange.dataStructure.binomialHashMap;

import stockExchange.dataStructure.binomialHeap.SellBinomialMinHeap;
import stockExchange.market.Order;

import java.util.HashMap;
import java.util.Map;

public class BinomialHashMap {
    private final HashMap<Double, SellBinomialMinHeap> binomialHashMap;
    public int ordersCompleted = 0;
    public int partialCompleted = 0;

    public BinomialHashMap() {
        this.binomialHashMap = new HashMap<>();
    }

    public void insert(Order order) {
        if (binomialHashMap.containsKey(order.getPrice())) {
            SellBinomialMinHeap binomialHeap = binomialHashMap.get(order.getPrice());
            binomialHeap.insert(order);
        } else {
            SellBinomialMinHeap binomialHeap = new SellBinomialMinHeap();
            binomialHeap.insert(order);
            binomialHashMap.put(order.getPrice(), binomialHeap);
        }
    }

    public Long fetchOrder(double price, long quantity) {
        long quantityFulfilled = 0;
        if (!binomialHashMap.containsKey(price)) {
            return quantityFulfilled;
        }
        SellBinomialMinHeap binomialHeap = binomialHashMap.get(price);
        //checking if the binomial tree is empty
        if (binomialHeap.isEmpty()) {
            return quantityFulfilled;
        }
        Order order = binomialHeap.peekHighestOrder();
        if (order.getQuantity() == quantity) {
            quantityFulfilled = quantity;
            binomialHeap.extractHighestPriorityElement();
            ordersCompleted++;
        } else if (order.getQuantity() < quantity) {
//            quantity = quantity - order.quantity;
            quantityFulfilled = order.getQuantity();
            binomialHeap.extractHighestPriorityElement();
            ordersCompleted++;
            quantityFulfilled += fetchOrder(price, quantity - quantityFulfilled);
        } else {
            order.setQuantity(order.getQuantity() - quantity);
            quantityFulfilled = quantity;// this means that we have processed the quantity of the buy shares
            partialCompleted++;
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
