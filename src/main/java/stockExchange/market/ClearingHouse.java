package stockExchange.market;

import stockExchange.dataStructure.binomialHashMap.BinomialHashMap;
import stockExchange.dataStructure.binomialHeap.BuyBinomialMaxHeap;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A ClearingHouse class that manages buy and sell orders and performs transactions between them.
 */
public class ClearingHouse {

    /**
     * Reader for buying orders.
     */
    private final OrderReader buyReader;

    /**
     * Reader for selling orders.
     */
    private final OrderReader sellReader;

    /**
     * BinomialHashMap for pending sell orders.
     */
    private BinomialHashMap pendingSellOrders;

    /**
     * BuyBinomialMaxHeap for pending buy orders.
     */
    private BuyBinomialMaxHeap pendingBuyOrders;

    /**
     * Constructs a ClearingHouse with buying and selling OrderReaders.
     *
     * @throws FileNotFoundException if any of the data files are not found.
     */
    public ClearingHouse() throws FileNotFoundException {
        this.buyReader = new OrderReader("SortedBuyerDataFile1.csv");
        this.sellReader = new OrderReader("SortedSellerDataFile1.csv");
    }

    /**
     * Fetches all buy orders that are received at the same time.
     *
     * @return a BuyBinomialMaxHeap of all buy orders at the current time.
     * @throws IOException if an I/O error occurs.
     */
    public BuyBinomialMaxHeap fetchBuyOrders() throws IOException {
        long startTime = buyReader.getTime();
        BuyBinomialMaxHeap buyOrders = new BuyBinomialMaxHeap();
        while (buyReader.getTime() == startTime) {
            Order order = buyReader.getOrder();
            buyOrders.insert(order);
        }
        if (pendingBuyOrders != null && pendingBuyOrders.getSize() != 0) {
            buyOrders.merge(pendingBuyOrders.getRoot());
        }
        return buyOrders;
    }

    /**
     * Fetches all sell orders that are received at the same time.
     *
     * @return a BinomialHashMap of all sell orders at the current time.
     * @throws IOException if an I/O error occurs.
     */
    public BinomialHashMap fetchSellOrders() throws IOException {
        long startTime = sellReader.getTime();
        BinomialHashMap sellOrders = new BinomialHashMap();
        while (sellReader.getTime() == startTime) {
            Order order = sellReader.getOrder();
            sellOrders.insert(order);
        }
        if (pendingSellOrders != null) {
            sellOrders.merge(pendingSellOrders);
        }
        return sellOrders;
    }

    /**
     * Performs transactions between buy and sell orders.
     *
     * @param buyOrders a BuyBinomialMaxHeap of buy orders.
     * @param sellOrders a BinomialHashMap of sell orders.
     */
    public void performTransactions(BuyBinomialMaxHeap buyOrders, BinomialHashMap sellOrders) {
        pendingBuyOrders = new BuyBinomialMaxHeap();
        pendingSellOrders = new BinomialHashMap();
        while (!buyOrders.isEmpty()) {
            Order order = buyOrders.extractHighestPriorityElement().getKey();
            Long quantityFulfilled = sellOrders.fetchOrder(order.getPrice(), order.getQuantity());
            if (quantityFulfilled != 0) {
                if (quantityFulfilled == order.getQuantity()) {
                    buyOrders.extractHighestPriorityElement();
                    System.out.println(order + " fulfilled");
                } else {
                    long remainingQuantity = order.getQuantity() - quantityFulfilled;
                    order.setQuantity(remainingQuantity);
                    pendingBuyOrders.insert(order);
                    System.out.println(order + " partially fulfilled, remaining quantity: " + remainingQuantity);
                }
            } else {
                //insert the node in the new binomial tree
                pendingBuyOrders.insert(order);
            }
        }
        pendingSellOrders = sellOrders;
    }

    /**
     * Checks whether the end of day (EOD) has been reached by verifying whether the time stamp of both the buyReader and
     * sellReader is -1.
     *
     * @return true if EOD is reached, false otherwise
     * @throws IOException if there is an error in reading the data file
     */
    public boolean isEOD() throws IOException {
        return buyReader.getTime() == -1 || sellReader.getTime() == -1;
    }
}
