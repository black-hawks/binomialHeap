package binomialHeap.dataStructure.binomialHeap;

// BinomialHeap
public abstract class BinomialHeap {

    // Member variables of this class
    protected BinomialHeapNode Nodes;
    private int size;

    // Constructor of this class
    public BinomialHeap() {
        Nodes = null;
        size = 0;
    }

    // Checking if heap is empty
    public boolean isEmpty() {
        return Nodes == null;
    }

    // Method 1
    // To get the size
    public int getSize() {
        return size;
    }

    // Method 2
    // Clear heap
    public void makeEmpty() {
        Nodes = null;
        size = 0;
    }

    // Method 3
    // To insert
    public void insert(Order order) {

        if (order != null) {
            BinomialHeapNode temp
                    = new BinomialHeapNode(order);
            if (Nodes == null) {
                Nodes = temp;
                size = 1;
            } else {
                unionNodes(temp);
                size++;
            }
        }
    }

    // Method 4
    // To unite two binomial heaps
    protected void merge(BinomialHeapNode newNode) {
        BinomialHeapNode temp1 = Nodes, temp2 = newNode;

        while ((temp1 != null) && (temp2 != null)) {

            if (temp1.degree == temp2.degree) {

                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            } else {

                if (temp1.degree < temp2.degree) {

                    if ((temp1.sibling == null)
                            || (temp1.sibling.degree
                            > temp2.degree)) {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    } else {
                        temp1 = temp1.sibling;
                    }
                } else {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;

                    if (tmp == Nodes) {
                        Nodes = temp1;
                    }
                }
            }
        }

        if (temp1 == null) {
            temp1 = Nodes;

            while (temp1.sibling != null) {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        } else {
        }
    }

    /**
     * To Join New Node with an Existing Heap
     * @param newNode
     */
    protected abstract void unionNodes(BinomialHeapNode newNode);


    /** To extract the node with the HighestPriority
     *
     * @return
     */
    public abstract BinomialHeapNode extractHighestPriorityElement();

    protected void reStructureHeapAfterExtract(BinomialHeapNode priorityNode) {
        BinomialHeapNode temp = Nodes, prevTemp = null;

        // find the priorityNode in the tree
        // if the Nodes is root element then priorityNode will be Nodes
        // if the Nodes is not root element then priorityNode will be in first siblings
        while (temp.key != priorityNode.key) {
            prevTemp = temp;
            temp = temp.sibling;
        }
        // if priorityNode was root
        if (prevTemp == null) {
            Nodes = temp.sibling;
        } else {
            // if priorityNode was in first sibling
            prevTemp.sibling = temp.sibling;
        }

        temp = temp.child;
        BinomialHeapNode fakeNode = temp;
        // remove the parent of all sibling of temp
        while (temp != null) {
            temp.parent = null;
            temp = temp.sibling;
        }
        //if there is no node left
        if ((Nodes == null) && (fakeNode == null)) {
            size = 0;
        } else {
            if ((Nodes == null) && (fakeNode != null)) {
                Nodes = fakeNode.reverse(null);
                size = Nodes.getSize();
            } else {
                if ((Nodes != null) && (fakeNode == null)) {
                    size = Nodes.getSize();
                } else {
                    unionNodes(fakeNode.reverse(null));
                    size = Nodes.getSize();
                }
            }
        }
    }

    // Method 10
    // To display heap
    public void displayHeap() {
        System.out.print("\nHeap : ");
        displayHeap(Nodes);
        System.out.println("\n");
    }

    private void displayHeap(BinomialHeapNode r) {
        if (r != null) {
            displayHeap(r.child);
            System.out.println(r.key.toString() + "  ");
            displayHeap(r.sibling);
        }
    }
}

