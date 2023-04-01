package binomialHeap.dataStructure.binomialHeap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BinomialHeapNodeTest {
    @Test
    public void testSize() {
        Order order1 = new Order(100, 10000, 1672531270, Orderer.InsiderOrder);
        BinomialHeapNode binomialHeapNode = new BinomialHeapNode(order1);
        Order order2 = new Order(100, 10000, 1672531270, Orderer.InsiderOrder);
        binomialHeapNode.sibling = new BinomialHeapNode(order2);
        Order order3 = new Order(100, 10000, 1672531270, Orderer.InsiderOrder);
        binomialHeapNode.child = new BinomialHeapNode(order3);
        Assertions.assertEquals(3, binomialHeapNode.getSize());
    }

}