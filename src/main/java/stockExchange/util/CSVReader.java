package stockExchange.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * CSVReader is a utility class for reading CSV files.
 * <p>
 * CSVReader can read CSV files and return each line as an array of String elements,
 * where each element is separated by a specified delimiter.
 */
public class CSVReader {
    public String filepath;
    public String delimiter;
    protected BufferedReader br;

    /**
     * Constructs a new CSVReader with default delimiter (",").
     *
     * @param filepath the path of the CSV file to read.
     * @throws FileNotFoundException if the file at the given filepath is not found.
     */
    public CSVReader(String filepath) throws FileNotFoundException {
        this(filepath, ",");
    }

    /**
     * Constructs a new CSVReader with a specified delimiter.
     *
     * @param filepath  the path of the CSV file to read.
     * @param delimiter the delimiter to use for splitting the lines of the CSV file.
     * @throws FileNotFoundException if the file at the given filepath is not found.
     */
    public CSVReader(String filepath, String delimiter) throws FileNotFoundException {
        this.filepath = filepath;
        this.delimiter = delimiter;
        br = new BufferedReader(new FileReader(filepath));
    }

    /**
     * Reads a single line from the CSV file and returns the elements as an array of String.
     *
     * @return an array of String elements, where each element corresponds to a field in the CSV file.
     * @throws IOException if an I/O error occurs.
     */
    public String[] readLine() throws IOException {
        String line = br.readLine();
        if (line == null) {
            return null;
        }
        return line.split(delimiter);
    }
}
