package stockExchange.dataStructure.binomialHeap;

import stockExchange.market.Order;

import java.util.Random;

/**
 * The Prioritizer class contains static methods for prioritizing orders in a binomial heap data structure
 * based on either min-heap or max-heap priority.
 */
public class Prioritizer {

    /**
     * Determines the priority order of two nodes in a min-heap.
     *
     * @param newNode the new node to compare
     * @param nextNode the next node to compare
     * @return true if newNode has higher priority than nextNode; false otherwise
     */
    public static boolean minHeapPrioritizer(BinomialHeapNode newNode, BinomialHeapNode nextNode) {

        Order newOrder = newNode.key, nextOrder = nextNode.key;
        BinomialHeapNode firstNode;

        //case 1
        if (newOrder.getPrice() < nextOrder.getPrice()) {
            firstNode = newNode;
        } else if (newOrder.getPrice() == nextOrder.getPrice()) {
            // if time is less for new order
            firstNode = priotizeByTimeStampOrOrderedBy(newOrder, nextOrder, newNode, nextNode);

            // if price is more for new order
        } else {
            firstNode = nextNode;
        }
        return firstNode == newNode;

    }

    /**
     * Determines the priority order of two nodes in a max-heap.
     *
     * @param newNode the new node to compare
     * @param nextNode the next node to compare
     * @return true if newNode has higher priority than nextNode; false otherwise
     */
    public static boolean maxHeapPrioritizer(BinomialHeapNode newNode, BinomialHeapNode nextNode) {

        Order newOrder = newNode.key, nextOrder = nextNode.key;
        BinomialHeapNode firstNode;

        //case 1
        if (newOrder.getPrice() > nextOrder.getPrice()) {
            firstNode = newNode;
        } else if (newOrder.getPrice() == nextOrder.getPrice()) {
            firstNode = priotizeByTimeStampOrOrderedBy(newOrder, nextOrder, newNode, nextNode);
            // if price is less for new order
        } else {
            firstNode = nextNode;
        }
        return firstNode == newNode;

    }

    /**
     * Helper method that determines the priority order of two nodes based on timestamp and orderer priority.
     *
     * @param newOrder the new order to compare
     * @param nextOrder the next order to compare
     * @param newNode the new node to compare
     * @param nextNode the next node to compare
     * @return the node with higher priority
     */
    private static BinomialHeapNode priotizeByTimeStampOrOrderedBy(Order newOrder, Order nextOrder, BinomialHeapNode newNode, BinomialHeapNode nextNode) {
        // if time is less for new order
        BinomialHeapNode firstNode;
        if (newOrder.getTimestamp() < nextOrder.getTimestamp()) {
            firstNode = newNode;
        }

        // if time is equal
        else if (newOrder.getTimestamp() == nextOrder.getTimestamp()) {
            // prioritize the order by source
            if (newOrder.getOrderedBy().priorityRank < nextOrder.getOrderedBy().priorityRank) {
                firstNode = newNode;
            }
            // if both has same type of orderer
            else if (newOrder.getOrderedBy().priorityRank == nextOrder.getOrderedBy().priorityRank) {
                // Randomly assign values
                Random rand = new Random(100);
                int one = 0;
                int two = 0;
                while (one == two) {
                    one = rand.nextInt(2);
                    two = rand.nextInt(2);
                }
                if (one < two) {
                    firstNode = newNode;
                } else {
                    firstNode = nextNode;
                }

            }
            // prioritze by order
            else {
                firstNode = nextNode;
            }
        }
        // if time is after for new order
        else {
            firstNode = nextNode;
        }
        return firstNode;
    }

}


