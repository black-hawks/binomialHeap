package binomialHeap.dataStructure.binomialHeap;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Order {

    double price;
    long quantity;
    long timestamp;

    Orderer orderedBy;

    public Order(double price, long quantity, long timestamp, Orderer orderedBy) {
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.orderedBy = orderedBy;
    }


    public double getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Orderer getOrderedBy() {
        return orderedBy;
    }

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", timestamp=" + timestamp +
                ", orderedBy=" + orderedBy +
                '}';
    }

    public void setQuantity(long quantity){
        this.quantity = quantity;
    }

}
