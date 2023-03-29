package binomialHeap.market;

import binomialHeap.dataStructure.binomialHeap.Order;
import binomialHeap.dataStructure.binomialHeap.Orderer;
import binomialHeap.util.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;

/**
 * TransactionReader class reads transaction information from a CSV file and returns transaction data in Transaction objects.
 * <p>
 * The transaction data includes transaction ID, sender address, receiver address, transaction amount, and transaction timestamp.
 */
public class OrderReader extends CSVReader {
    private final Duration period;

    /**
     * Constructor for TransactionReader class.
     *
     * @param filepath the file path of the CSV file to be read
     * @param period   the duration of time to read transactions from the CSV file
     * @throws FileNotFoundException if the CSV file is not found
     */
    public OrderReader(String filepath, Duration period) throws FileNotFoundException {
        super(filepath);
        this.period = period;
    }


    /**
     * Gets the timestamp of the first transaction in the current period from the CSV file.
     *
     * @return the timestamp of the first transaction in the current period in milliseconds since the epoch,
     * or -1 if there are no more transactions in the CSV file
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if the timestamp format is not valid
     */
    public long getTime() throws IOException, ParseException {
        br.mark(100);
        String[] line = this.readLine();
        if (line == null) {
            return -1;
        }
        this.br.reset();
        return Long.parseLong(line[2]);
    }

    /**
     * Gets the transaction data from the CSV file and creates a Transaction object.
     *
     * @return a Transaction object containing the transaction data, or null if there are no more transactions in the CSV file
     * @throws IOException    if an I/O error occurs
     * @throws ParseException if the timestamp, amount or address format is not valid
     */
    public Order getOrder() throws IOException, ParseException {
        String[] line = this.readLine();
        if (line == null) {
            return null;
        }
        return new Order(
                Double.parseDouble(line[0]),
                Long.parseLong(line[1]),
                Long.parseLong(line[2]),
                Orderer.valueOf(line[3])
        );
    }

    /**
     * Gets a list of transactions within the current time period.
     *
     * @return a list of Transaction objects within the current time period, or null if there are no more transactions in the CSV file
     */
//    public List<Order> getOrders() {
//        try {
//            long startTime = getTime();
//            if (startTime == -1) {
//                return null;
//            }
//            long endTime = startTime + (period.getSeconds() * 1000);
//            List<Transaction> transactions = new ArrayList<>();
//            while (getTime() != -1 && getTime() < endTime) {
//                transactions.add(getTransaction());
//            }
//            return transactions;
//        } catch (IOException | ParseException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
}
