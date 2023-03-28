import binomialHeap.dataStructure.binomialHeap.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        BinomialHeap sellbinHeap = new SellBinomialMinHeap();

        BinomialHeap buybinHeap = new BuyBinomialMaxHeap();

        // Inserting in the binomial heap
        // Custom input integer values
        Order order = new Order(44.5,10, LocalDateTime.now(), Orderer.MutualFundOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);
        order = new Order(42.5,10, LocalDateTime.now(), Orderer.ClientOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);
        order = new Order(42.5,10, LocalDateTime.now(), Orderer.ClientOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);
        order = new Order(47.5,10, LocalDateTime.now(), Orderer.ClientOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);
        order = new Order(44.5,10, LocalDateTime.now(), Orderer.ClientOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);
        order = new Order(53.5,10, LocalDateTime.now(), Orderer.ClientOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);
        order = new Order(40.5,10, LocalDateTime.now(), Orderer.ClientOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);
        order = new Order(53.5,10, LocalDateTime.now(), Orderer.ClientOrder);
        sellbinHeap.insert(order);
        buybinHeap.insert(order);

        // Size of binomial heap
        System.out.println("Size of the Sell binomial heap is "
                + sellbinHeap.getSize());

        // Displaying the binomial heap
        sellbinHeap.displayHeap();

        System.out.println("Size of the Buy binomial heap is "
                + buybinHeap.getSize());

        // Displaying the binomial heap
        buybinHeap.displayHeap();

        // Deletion in binomial heap
//        binHeap.delete(15);
//        binHeap.delete(8);
        System.out.println(sellbinHeap.extractHighestPriorityElement());
        System.out.println(sellbinHeap.extractHighestPriorityElement());
        System.out.println(sellbinHeap.extractHighestPriorityElement());

        // Size of binomial heap
        System.out.println("Size of the binomial heap is "
                + sellbinHeap.getSize());

        // Displaying the binomial heap
        sellbinHeap.displayHeap();

        System.out.println(buybinHeap.extractHighestPriorityElement());
        System.out.println(buybinHeap.extractHighestPriorityElement());
        System.out.println(buybinHeap.extractHighestPriorityElement());

        System.out.println("Size of the Buy binomial heap is "
                + buybinHeap.getSize());

        // Displaying the binomial heap
        buybinHeap.displayHeap();

        // Making the heap empty
        sellbinHeap.makeEmpty();

        // checking if heap is empty
        System.out.println(sellbinHeap.isEmpty());
    }
}
