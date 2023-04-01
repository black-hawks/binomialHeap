package binomialHeap.dataStructure.binomialHeap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BinomialHashMapTest {

    @Test
    void testEqualQuantity() {
        BinomialHashMap binomialHashMap = new BinomialHashMap();
        Order order1 = new Order(100, 5000, 1672531270, Orderer.InsiderOrder);
        Order order2 = new Order(100, 10000, 1672531270, Orderer.ClientOrder);
        binomialHashMap.insert(order1);
        binomialHashMap.insert(order2);
        long quantityFulfilled = binomialHashMap.fetchOrder(100, 15000);
        Assertions.assertEquals(15000, quantityFulfilled);
    }

    @Test
    void testLessBuyerQuantity() {
        BinomialHashMap binomialHashMap = new BinomialHashMap();
        Order order1 = new Order(100, 5000, 1672531270, Orderer.InsiderOrder);
        Order order2 = new Order(100, 10000, 1672531270, Orderer.ClientOrder);
        binomialHashMap.insert(order1);
        binomialHashMap.insert(order2);
        long quantityFulfilled = binomialHashMap.fetchOrder(100, 9000);
        Assertions.assertEquals(9000, quantityFulfilled);
        SellBinomialMinHeap remainingSellOrders = binomialHashMap.getBinomialHashMap().get(100.0);
        Assertions.assertEquals(2, remainingSellOrders.getSize());
        Order remainingOrder1 = remainingSellOrders.extractHighestPriorityElement().getKey();
        Assertions.assertEquals(Orderer.ClientOrder, remainingOrder1.getOrderedBy());
        Assertions.assertEquals(1000, remainingOrder1.getQuantity());
        Order remainingOrder2 = remainingSellOrders.extractHighestPriorityElement().getKey();
        Assertions.assertEquals(Orderer.InsiderOrder, remainingOrder2.getOrderedBy());
        Assertions.assertEquals(5000, remainingOrder2.getQuantity());
    }

    @Test
    public void testUnavailablePrice() {
        BinomialHashMap binomialHashMap = new BinomialHashMap();
        Order order1 = new Order(100, 5000, 1672531270, Orderer.InsiderOrder);
        binomialHashMap.insert(order1);
        long quantityFulfilled = binomialHashMap.fetchOrder(101, 9000);
        Assertions.assertEquals(0, quantityFulfilled);
    }

    @Test
    public void mergeBinomialHashMap() {
        BinomialHashMap binomialHashMap1 = new BinomialHashMap();
        Order order1 = new Order(100, 5000, 1672531271, Orderer.InsiderOrder);
        binomialHashMap1.insert(order1);
        BinomialHashMap binomialHashMap2 = new BinomialHashMap();
        Order order2 = new Order(100, 10000, 1672531270, Orderer.InsiderOrder);
        Order order3 = new Order(101, 70000, 1672531272, Orderer.InsiderOrder);
        binomialHashMap2.insert(order2);
        binomialHashMap2.insert(order3);
        binomialHashMap1.merge(binomialHashMap2);
        SellBinomialMinHeap mergedSellOrders1 = binomialHashMap1.getBinomialHashMap().get(100.0);
        Assertions.assertEquals(2, mergedSellOrders1.getSize());
        Assertions.assertEquals(order2, mergedSellOrders1.extractHighestPriorityElement().getKey());
        Assertions.assertEquals(order1, mergedSellOrders1.extractHighestPriorityElement().getKey());

        SellBinomialMinHeap mergedSellOrders2 = binomialHashMap1.getBinomialHashMap().get(101.0);
        Assertions.assertEquals(1, mergedSellOrders2.getSize());
        Assertions.assertEquals(order3, mergedSellOrders2.extractHighestPriorityElement().getKey());
//        Assertions.assertEquals(order3, mergedSellOrders.extractHighestPriorityElement().getKey());
    }

}