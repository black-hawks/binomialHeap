package stockExchange.dataStructure.binomialHeap;

import stockExchange.market.Order;

/**
 * BinomialHeap is an abstract class representing a binomial heap data structure.
 * A binomial heap is a collection of binomial trees that satisfies the heap property,
 * where the key of any node is greater than or equal to the keys of its children.
 */
public abstract class BinomialHeap {

    /**
     * Nodes is a pointer to the root node of the heap.
     */
    protected BinomialHeapNode Nodes;


    /**
     * size stores the number of elements in the heap.
     */
    private int size;

    /**
     * Initializes an empty binomial heap.
     */
    public BinomialHeap() {
        Nodes = null;
        size = 0;
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap is empty, false otherwise.
     */
    public boolean isEmpty() {
        return Nodes == null;
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return the number of elements in the heap.
     */
    public int getSize() {
        return size;
    }

    /**
     * Removes all elements from the heap.
     */
    public void makeEmpty() {
        Nodes = null;
        size = 0;
    }

    /**
     * Inserts an order into the heap.
     *
     * @param order the order to be inserted.
     */
    public void insert(Order order) {

        if (order != null) {
            BinomialHeapNode temp
                    = new BinomialHeapNode(order);
            if (Nodes == null) {
                Nodes = temp;
                size = 1;
            } else {
                unionNodes(temp);
//                size++;
            }
        }
    }

    /**
     * Merges the given binomial heap with this heap.
     *
     * @param newNode the binomial heap to be merged with this heap.
     */
    public void merge(BinomialHeapNode newNode) {
        int newNodeSize = newNode.getSize();
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
        }
        size += newNodeSize;
    }

    /**
     * Joins a new node with an existing heap.
     *
     * @param newNode the node to be joined.
     */
    protected abstract void unionNodes(BinomialHeapNode newNode);


    /**
     * This method extracts the highest priority element from the binomial heap and returns it.
     *
     * @return the highest priority element from the binomial heap
     */
    public abstract BinomialHeapNode extractHighestPriorityElement();

    /**
     * This method restructures the binomial heap after extracting the priority node.
     *
     * @param priorityNode the node with highest priority that was extracted from the binomial heap
     */
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

    /**
     * This method displays the contents of the binomial heap.
     */
    public void displayHeap() {
        System.out.print("\nHeap : ");
        displayHeap(Nodes);
        System.out.println("\n");
    }

    /**
     * This method recursively displays the contents of the binomial heap.
     *
     * @param r the root node of the binomial heap
     */
    private void displayHeap(BinomialHeapNode r) {
        if (r != null) {
            displayHeap(r.child);
            System.out.println(r.key.toString() + "  ");
            displayHeap(r.sibling);
        }
    }

    /**
     * This method returns the root node of the binomial heap.
     *
     * @return the root node of the binomial heap
     */
    public BinomialHeapNode getRoot() {
        return Nodes;
    }

    /**
     * This method returns the highest order of the binomial heap.
     *
     * @return the highest order of the binomial heap
     */
    public Order peekHighestOrder() {
        if (Nodes == null)
            return null;
        BinomialHeapNode maxNode = Nodes.findMaxNode();
        return maxNode.getKey();
    }


}

