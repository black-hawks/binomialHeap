package stockExchange.dataStructure.binomialHeap;

import stockExchange.market.Order;

/**
 * The BinomialHeapNode class represents a node in a binomial heap.
 * It stores an Order object as the key, along with other necessary fields
 * such as degree, parent, sibling, and child for maintaining the structure
 * of the binomial heap.
 */
public class BinomialHeapNode {

    /**
     * The Order object that represents the key of this node.
     */
    Order key;

    /**
     * The degree of this node, which is the number of children it has.
     */
    int degree;

    /**
     * The parent of this node.
     */
    BinomialHeapNode parent;

    /**
     * The sibling of this node.
     */
    BinomialHeapNode sibling;

    /**
     * The child of this node.
     */
    BinomialHeapNode child;

    /**
     * Constructs a BinomialHeapNode object with the specified Order object as its key.
     *
     * @param order the Order object that represents the key of this node.
     */
    public BinomialHeapNode(Order order) {

        key = order;
        degree = 0;
        parent = null;
        sibling = null;
        child = null;
    }

    /**
     * Returns the Order object that represents the key of this node.
     *
     * @return the Order object that represents the key of this node.
     */
    public Order getKey() {
        return key;
    }

    /**
     * Returns a new BinomialHeapNode object that is the result of reversing the
     * sibling chain of this node.
     *
     * @param sibling the sibling of this node.
     * @return a new BinomialHeapNode object that is the result of reversing the
     * sibling chain of this node.
     */
    public BinomialHeapNode reverse(BinomialHeapNode sibling) {
        BinomialHeapNode ret;
        if (this.sibling != null)
            ret = this.sibling.reverse(this);
        else
            ret = this;
        this.sibling = sibling;
        return ret;
    }

    /**
     * Returns the node with the minimum key value in the subtree rooted at this node.
     *
     * @return the node with the minimum key value in the subtree rooted at this node.
     */
    public BinomialHeapNode findMinNode() {
        BinomialHeapNode min = this;
        BinomialHeapNode x = this.sibling;
        while (x != null) {
            if (Prioritizer.minHeapPrioritizer(x, min)) {
                min = x;
            }
            x = x.sibling;
        }
        return min;
    }

    /**
     * Returns the node with the maximum key value in the subtree rooted at this node.
     *
     * @return the node with the maximum key value in the subtree rooted at this node.
     */
    public BinomialHeapNode findMaxNode() {
        BinomialHeapNode max = this;
        BinomialHeapNode x = this.sibling;
        while (x != null) {
            if (Prioritizer.maxHeapPrioritizer(x, max)) {
                max = x;
            }
            x = x.sibling;
        }
        return max;
    }

    /**
     * Returns the node in the subtree rooted at this node with the specified key value.
     *
     * @param price the key value to search for.
     * @return the node in the subtree rooted at this node with the specified key value,
     * or null if no such node exists.
     */
    public BinomialHeapNode findANodeWithKey(float price) {

        BinomialHeapNode temp = this, node = null;

        while (temp != null) {
            if (temp.key.getPrice() == price) {
                node = temp;
                break;
            }

            if (temp.child == null)
                temp = temp.sibling;

            else {
                node = temp.child.findANodeWithKey(price);
                if (node == null)
                    temp = temp.sibling;
                else
                    break;
            }
        }

        return node;
    }

    /**
     * Returns the size of the binomial heap rooted at this node, including this node.
     * The size of the heap is defined as the total number of nodes in the heap.
     *
     * @return the size of the binomial heap rooted at this node
     */
    public int getSize() {
        return (
                1 + ((child == null) ? 0 : child.getSize())
                        + ((sibling == null) ? 0 : sibling.getSize()));
    }
}
