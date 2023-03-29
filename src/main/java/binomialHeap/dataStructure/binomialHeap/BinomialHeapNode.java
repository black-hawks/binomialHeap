package binomialHeap.dataStructure.binomialHeap;

public class BinomialHeapNode {

    Order key;
    int degree;
    BinomialHeapNode parent;
    BinomialHeapNode sibling;
    BinomialHeapNode child;

    // Constructor of this class
    public BinomialHeapNode(Order order) {

        key = order;
        degree = 0;
        parent = null;
        sibling = null;
        child = null;
    }

    public Order getKey() {
        return key;
    }

    // Method 1
    // To reverse
    public BinomialHeapNode reverse(BinomialHeapNode sibling) {
        BinomialHeapNode ret;
        if (this.sibling != null)
            ret = this.sibling.reverse(this);
        else
            ret = this;
        this.sibling = sibling;
        return ret;
    }

    // Method 2
    // To find minimum node
    public BinomialHeapNode findMinNode() {

        // this keyword refers to current instance itself
        BinomialHeapNode x = this, y = this;
        double min = x.key.getPrice();

        while (x != null) {
            if (x.key.getPrice() < min) {
                y = x;
                min = x.key.getPrice();
            }

            x = x.sibling;
        }

        return y;
    }

    public BinomialHeapNode findMaxNode() {

        // this keyword refers to current instance itself
        BinomialHeapNode x = this, y = this;
        double max = x.key.getPrice();

        while (x != null) {
            if (x.key.getPrice() > max) {
                y = x;
                max = x.key.getPrice();
            }

            x = x.sibling;
        }

        return y;
    }

    // Method 3
    // To find node with key value
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

    // Method 4
    // To get the size
    public int getSize() {
        return (
                1 + ((child == null) ? 0 : child.getSize())
                        + ((sibling == null) ? 0 : sibling.getSize()));
    }
}
