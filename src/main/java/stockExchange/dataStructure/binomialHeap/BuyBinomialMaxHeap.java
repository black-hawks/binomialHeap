package stockExchange.dataStructure.binomialHeap;

/**
 * BuyBinomialMaxHeap is a subclass of BinomialHeap that implements the Buy Max Heap data structure.
 * It overrides the unionNodes method to merge a new node into the heap and ensures that the
 * max heap property is maintained. It also provides the extractHighestPriorityElement method to
 * extract the node with the highest priority from the heap and restructures the heap after the extraction.
 */
public class BuyBinomialMaxHeap extends BinomialHeap {

    /**
     * This method merges the given new node into the heap and ensures that the max heap property is maintained.
     *
     * @param newNode the new node to be merged into the heap
     */
    @Override
    protected void unionNodes(BinomialHeapNode newNode) {
        merge(newNode);

        BinomialHeapNode prevTemp = null, temp = Nodes,
                nextTemp = Nodes.sibling;

        while (nextTemp != null) {

            if ((temp.degree != nextTemp.degree)
                    || ((nextTemp.sibling != null)
                    && (nextTemp.sibling.degree
                    == temp.degree))) {
                prevTemp = temp;
                temp = nextTemp;
            } else {
                if (Prioritizer.maxHeapPrioritizer(temp, nextTemp)) {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                } else {
                    if (prevTemp == null) {
                        Nodes = nextTemp;
                    } else {
                        prevTemp.sibling = nextTemp;
                    }
                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }

    /**
     * This method extracts the node with the highest priority from the heap and restructures the heap
     * to maintain the max heap property.
     *
     * @return the node with the highest priority in the heap
     */
    public BinomialHeapNode extractHighestPriorityElement() {
        if (Nodes == null)
            return null;
        BinomialHeapNode maxNode = Nodes.findMaxNode();
        reStructureHeapAfterExtract(maxNode);
        return maxNode;
    }
}
