package stockExchange.market;

import stockExchange.util.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The OrderReader class is responsible for reading orders from a CSV file.
 * It extends the CSVReader class to provide CSV reading functionality.
 */
public class OrderReader extends CSVReader {

    /**
     * Constructs an OrderReader object with the given file path.
     * Throws a FileNotFoundException if the file is not found.
     *
     * @param filepath the path of the CSV file to read orders from
     * @throws FileNotFoundException if the file is not found
     */
    public OrderReader(String filepath) throws FileNotFoundException {
        super(filepath);
    }

    /**
     * Reads the time stamp from the CSV file.
     *
     * @return the time stamp as a long integer
     * @throws IOException if an I/O error occurs while reading the file
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
     * Reads an order from the CSV file.
     *
     * @return an Order object representing the order read from the file
     * @throws IOException if an I/O error occurs while reading the file
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
