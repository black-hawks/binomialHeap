package stockExchange.market;

/**
 * The Order class represents a single order placed in a stock exchange market.
 */
public class Order {

    /**
     * The price at which the order is placed.
     */
    double price;

    /**
     * The quantity of shares to be traded in the order.
     */
    long quantity;

    /**
     * The timestamp at which the order is placed.
     */
    long timestamp;

    /**
     * The orderer who placed the order.
     */
    Orderer orderedBy;


    /**
     * Constructs a new Order object with the specified price, quantity, timestamp, and orderer.
     *
     * @param price     the price of the order
     * @param quantity  the quantity of shares to be traded in the order
     * @param timestamp the timestamp at which the order is placed
     * @param orderedBy the orderer who placed the order
     */
    public Order(double price, long quantity, long timestamp, Orderer orderedBy) {
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.orderedBy = orderedBy;
    }

    /**
     * Returns the price of the order.
     *
     * @return the price of the order
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the quantity of shares to be traded in the order.
     *
     * @return the quantity of shares to be traded in the order
     */
    public long getQuantity() {
        return quantity;
    }

    /**
     * Returns the timestamp at which the order is placed.
     *
     * @return the timestamp at which the order is placed
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the orderer who placed the order.
     *
     * @return the orderer who placed the order
     */
    public Orderer getOrderedBy() {
        return orderedBy;
    }

    /**
     * Returns a string representation of the Order object.
     *
     * @return a string representation of the Order object
     */
    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", timestamp=" + timestamp +
                ", orderedBy=" + orderedBy +
                '}';
    }

    /**
     * Sets the quantity of shares to be traded in the order.
     *
     * @param quantity the quantity of shares to be traded in the order
     */
    public void setQuantity(long quantity){
        this.quantity = quantity;
    }

}
