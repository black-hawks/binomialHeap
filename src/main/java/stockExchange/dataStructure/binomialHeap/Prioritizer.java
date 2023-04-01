package stockExchange.dataStructure.binomialHeap;

import stockExchange.market.Order;

import java.util.Random;

public class Prioritizer {

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


