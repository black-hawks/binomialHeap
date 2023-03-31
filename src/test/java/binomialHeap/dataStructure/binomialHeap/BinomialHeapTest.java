package binomialHeap.dataStructure.binomialHeap;

import org.junit.jupiter.api.Assertions;

class BinomialHeapTest {

    @org.junit.jupiter.api.Test
    void insert() {
        BuyBinomialMaxHeap buyBinomialMaxHeap = new BuyBinomialMaxHeap();
        Order order = new Order(100, 100, 1672531270, Orderer.ClientOrder);
        buyBinomialMaxHeap.insert(order);
        Assertions.assertEquals(order, buyBinomialMaxHeap.extractHighestPriorityElement().getKey());

    }
}