package stockExchange.dataStructure.binomialHeap;

/**
 * The SellBinomialMinHeap class extends BinomialHeap and represents a min-heap data structure
 * that is used to implement a sell priority queue in a stock exchange system.
 */
public class SellBinomialMinHeap extends  BinomialHeap{

    /**
     * Creates a new instance of the SellBinomialMinHeap class.
     */
    public SellBinomialMinHeap(){
        super();
    }

    /**
     * Overrides the unionNodes method from the parent class and merges a new node with the existing heap.
     *
     * @param newNode the node to be merged with the heap
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
                if (Prioritizer.minHeapPrioritizer(temp, nextTemp)) {
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
     * Extracts the highest priority element from the heap and restructures the heap accordingly.
     *
     * @return the node with the highest priority in the heap
     */
    public BinomialHeapNode extractHighestPriorityElement() {
        if (Nodes == null)
            return null;
        BinomialHeapNode minNode = Nodes.findMinNode();
        reStructureHeapAfterExtract(minNode);

        return minNode;
    }
}
