package binomialHeap.dataStructure.binomialHeap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BinomialHeapTest {

    @org.junit.jupiter.api.Test
    void insert() {
        BuyBinomialMaxHeap buyBinomialMaxHeap = new BuyBinomialMaxHeap();
        Order order = new Order(100, 100, 1672531270, Orderer.ClientOrder);
        buyBinomialMaxHeap.insert(order);
        Assertions.assertEquals(order, buyBinomialMaxHeap.extractHighestPriorityElement().getKey());

    }


    @Test
    void getSize() {
        BuyBinomialMaxHeap buyBinomialMaxHeap = new BuyBinomialMaxHeap();
        int numOfOrder = 10;
        for (int i = 0; i < numOfOrder; i++) {
            Order order = new Order(100, 100, 1672531270, Orderer.ClientOrder);
            buyBinomialMaxHeap.insert(order);
        }
        Assertions.assertEquals(numOfOrder, buyBinomialMaxHeap.getSize());
    }
}