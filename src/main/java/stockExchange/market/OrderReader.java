package stockExchange.market;

import stockExchange.util.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * TransactionReader class reads transaction information from a CSV file and returns transaction data in Transaction objects.
 * <p>
 * The transaction data includes transaction ID, sender address, receiver address, transaction amount, and transaction timestamp.
 */
public class OrderReader extends CSVReader {

    /**
     * Constructor for TransactionReader class.
     *
     * @param filepath the file path of the CSV file to be read
     * @throws FileNotFoundException if the CSV file is not found
     */
    public OrderReader(String filepath) throws FileNotFoundException {
        super(filepath);
    }


    /**
     * Gets the timestamp of the first transaction in the current period from the CSV file.
     *
     * @return the timestamp of the first transaction in the current period in milliseconds since the epoch,
     * or -1 if there are no more transactions in the CSV file
     * @throws IOException if an I/O error occurs
     */
    public long getTime() throws IOException {
        br.mark(100);
        String[] line = this.readLine();
        if (line == null) {
            return -1;
        }
        this.br.reset();
        return Long.parseLong(line[0]);
    }

    /**
     * Gets the transaction data from the CSV file and creates a Transaction object.
     *
     * @return a Transaction object containing the transaction data, or null if there are no more transactions in the CSV file
     * @throws IOException if an I/O error occurs
     */
    public Order getOrder() throws IOException {
        String[] line = this.readLine();
        if (line == null) {
            return null;
        }
        return new Order(
                Double.parseDouble(line[1]),
                Long.parseLong(line[2]),
                Long.parseLong(line[0]),
                Orderer.valueOf(line[3])
        );
    }
}
