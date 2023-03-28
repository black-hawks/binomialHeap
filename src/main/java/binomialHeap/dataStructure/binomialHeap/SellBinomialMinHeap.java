package binomialHeap.dataStructure.binomialHeap;

public class SellBinomialMinHeap extends  BinomialHeap{

    public SellBinomialMinHeap(){
        super();
    }

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

    public BinomialHeapNode extractHighestPriorityElement() {
        if (Nodes == null)
            return null;
        BinomialHeapNode minNode = Nodes.findMinNode();
        reStructureHeapAfterExtract(minNode);

        return minNode;
    }



}
