package binomialHeap.dataStructure.binomialHeap;

import java.util.Random;

public class Prioritizer {

    public static boolean minHeapPrioritizer(BinomialHeapNode newNode, BinomialHeapNode nextNode){

        Order newOrder = newNode.key, nextOrder= nextNode.key;
        BinomialHeapNode firstNode=null, SecondNode=null;

        //case 1
        if(newOrder.getPrice() < nextOrder.getPrice()){
            firstNode= newNode;
            SecondNode = nextNode;
        }else if(newOrder.getPrice() == nextOrder.getPrice()){
            // if time is less for new order
            firstNode = priotizeByTimeStampOrOrderedBy(newOrder, nextOrder, firstNode, newNode, SecondNode, nextNode);

            // if price is more for new order
        }else{
            firstNode= nextNode;
            SecondNode = newNode;
        }
        if(firstNode == newNode) {
            return true;
        }
        return false;

    }

    public static boolean maxHeapPrioritizer(BinomialHeapNode newNode, BinomialHeapNode nextNode){

        Order newOrder = newNode.key, nextOrder= nextNode.key;
        BinomialHeapNode firstNode=null, SecondNode=null;

        //case 1
        if(newOrder.getPrice() > nextOrder.getPrice()){
            firstNode= newNode;
            SecondNode = nextNode;
        }else if(newOrder.getPrice() == nextOrder.getPrice()){
            firstNode = priotizeByTimeStampOrOrderedBy(newOrder, nextOrder, firstNode, newNode, SecondNode, nextNode);
            // if price is less for new order
        }else{
            firstNode= nextNode;
            SecondNode = newNode;
        }
        if(firstNode == newNode) {
            return true;
        }
        return false;

    }

    private static BinomialHeapNode priotizeByTimeStampOrOrderedBy(Order newOrder, Order nextOrder, BinomialHeapNode firstNode, BinomialHeapNode newNode, BinomialHeapNode secondNode, BinomialHeapNode nextNode) {
        // if time is less for new order
        if(newOrder.getTimestamp().isBefore(nextOrder.getTimestamp())){
            firstNode = newNode;
            secondNode = nextNode;
        }

        // if time is equal
        else if(newOrder.getTimestamp().isEqual(nextOrder.getTimestamp())){
            // prioritize the order by source
            if(newOrder.getOrderedBy().priorityRank < nextOrder.getOrderedBy().priorityRank){
                firstNode = newNode;
                secondNode = nextNode;
            }
            // if both has same type of orderer
            else if(newOrder.getOrderedBy().priorityRank == nextOrder.getOrderedBy().priorityRank){
                // Randomly assign values
                Random rand=new Random(100);
                int one=0;
                int two=0;
                while(one == two){
                    one=rand.nextInt(2);
                    two=rand.nextInt(2);
                }
                if(one < two){
                    firstNode = newNode;
                    secondNode = nextNode;
                }
                else{
                    firstNode = nextNode;
                    secondNode = newNode;
                }

            }
            // prioritze by order
            else{
                firstNode = nextNode;
                secondNode = newNode;
            }
        }
        // if time is after for new order
        else{
            firstNode = nextNode;
            secondNode = newNode;
        }
        return firstNode;
    }

}


