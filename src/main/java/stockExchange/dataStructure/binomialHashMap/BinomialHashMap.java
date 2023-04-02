package stockExchange.dataStructure.binomialHashMap;

import stockExchange.dataStructure.binomialHeap.SellBinomialMinHeap;
import stockExchange.market.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * The BinomialHashMap class represents a data structure that implements a map of SellBinomialMinHeap objects, with
 * a Double key representing the price associated with each heap.
 */
public class BinomialHashMap {
    /**
     * The underlying HashMap to store SellBinomialMinHeap objects with a Double key.
     */
    private final HashMap<Double, SellBinomialMinHeap> binomialHashMap;
    public int ordersCompleted = 0;
    public int partialCompleted = 0;

    /**
     * Constructs a new empty BinomialHashMap object.
     */
    public BinomialHashMap() {
        this.binomialHashMap = new HashMap<>();
    }

    /**
     * Inserts a new Order object into the SellBinomialMinHeap object associated with the order's price, creating a new
     * heap if necessary.
     *
     * @param order the Order object to insert into the map
     */
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

    /**
     * Fetches the highest priority Order object with the specified price and quantity, and removes it from the
     * SellBinomialMinHeap object associated with that price. Returns the quantity of shares fulfilled by the order.
     *
     * @param price the price associated with the SellBinomialMinHeap object to fetch the order from
     * @param quantity the desired quantity of shares to fulfill
     * @return the quantity of shares fulfilled by the order
     */
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

    /**
     * Merges the SellBinomialMinHeap objects of another BinomialHashMap object with this one. If a key conflict arises,
     * the values associated with the conflicting key are merged.
     *
     * @param newBinomialHashMap the BinomialHashMap object to merge with this one
     */
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

    /**
     * Returns the binomial hash map that stores the prices and associated sell binomial min heaps.
     *
     * @return a {@code HashMap<Double, SellBinomialMinHeap>} representing the binomial hash map
     */
    public HashMap<Double, SellBinomialMinHeap> getBinomialHashMap() {
        return binomialHashMap;
    }
}
