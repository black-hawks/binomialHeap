package stockExchange.inputGeneration;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * The DataGenerator class generates data for a stock exchange simulation. The generated data is saved as
 * CSV files containing order details like timestamp, price, quantity, and source of the order.
 * The class contains methods to generate data for every second and for random seconds.
 */
public class DataGenerator {
  /**
   * The type of orders.
   */
  final static String CLIENT_ORDER = "ClientOrder";
  final static String FOREIGN_INVESTOR_ORDER = "ForeignInvestorOrder";
  final static String MARKET_CONTROLLER_ORDER = "MarketControllerOrder";
  final static String MUTUAL_FUND_ORDER = "MutualFundOrder";
  final static String ISSUER_ORDER = "IssuerOrder";
  final static String PROFESSIONAL_ORDER = "ProfessionalOrder";
  final static String INSIDER_ORDER = "InsiderOrder";

  /**
   * Array containing the type of orders.
   */
  final static String ORDER_SOURCE[] = {
          CLIENT_ORDER,
          FOREIGN_INVESTOR_ORDER,
          MARKET_CONTROLLER_ORDER,
          MUTUAL_FUND_ORDER,
          ISSUER_ORDER,
          PROFESSIONAL_ORDER,
          INSIDER_ORDER
  };

  /**
   * The approximate count of records to generate.
   */
  final static int APPROX_RECORD_COUNT = 6000000;

  /**
   * The minimum number of records per second to generate.
   */
  final static int MIN_RECORD_COUNT_PER_SECOND = 5000;

  /**
   * The maximum number of records per second to generate.
   */
  final static int MAX_RECORD_COUNT_PER_SECOND = 10000;

  /**
   * The total number of seconds to generate data for.
   */
  final static int TOTAL_SECONDS = 60 * 60; // one hour

  /**
   * The main method generates data for every second for TOTAL_SECONDS, and sorts the generated data.
   *
   * @param args the command line arguments.
   * @throws IOException if there is an error creating the CSV files.
   */
  public static void main(String args[]) throws IOException {
    String BuyerCsvFile = "BuyerDataFile1.csv";
    String SellerCsvFile = "SellerDataFile1.csv";
    int ctr = 1;
    int totalSeconds = 1000;

    do {
      generateRecordsForEverySecond(BuyerCsvFile, 1000);
      ExternalMergeSort externalMergeSort = new ExternalMergeSort();
      externalMergeSort.sort(BuyerCsvFile, "SortedBuyerDataFile" + ctr + ".csv");

      totalSeconds += 1000;
      BuyerCsvFile = "BuyerDataFile" + (++ctr) + ".csv";
    } while(totalSeconds < TOTAL_SECONDS);

    ctr = 1;
    totalSeconds = 1000;
    do {
      generateRecordsForEverySecond(SellerCsvFile, 1000);
      ExternalMergeSort externalMergeSort = new ExternalMergeSort();
      externalMergeSort.sort(SellerCsvFile, "SortedSellerDataFile" + ctr + ".csv");

      totalSeconds += 1000;
      SellerCsvFile = "SellerDataFile" + (++ctr) + ".csv";
    } while(totalSeconds < TOTAL_SECONDS);

//    generateRecordsForRandomSeconds(BuyerCsvFile);
//    generateRecordsForRandomSeconds(SellerCsvFile);

  }

  /**
   * Generates CSV records for every second for a specified duration.
   *
   * @param csvFile the file path where the CSV records will be written
   * @param totalSeconds the total number of seconds for which records will be generated
   */
  public static void generateRecordsForEverySecond(String csvFile, int totalSeconds) {
    int numberOfRecordsGenerated = 0;
    try(FileWriter writer = new FileWriter(csvFile)) {
      Timestamp original = Timestamp.valueOf(String.format("2023-01-01 00:00:00"));

      for (int sec = 0; sec < totalSeconds; sec++) {
        // generate 5000 to 10000 records for a particular second
        numberOfRecordsGenerated += generateRecordsForAParticularSec(original, writer);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(original.getTime());
        cal.add(Calendar.SECOND, 1);
        original = new Timestamp(cal.getTime().getTime());
      }

      writer.flush();
      writer.close();
      System.out.println("CSV file created successfully. " + numberOfRecordsGenerated + " records generated.");
    } catch (IOException e) {
      System.err.println("Error creating CSV file: " + e.getMessage());
    }
  }

  /**
   * Generates records for random timestamps and writes them to a CSV file.

   * @param csvFile the path of the CSV file to be created
   */
  public static void generateRecordsForRandomSeconds(String csvFile) {

    try(FileWriter writer = new FileWriter(csvFile)) {
      // code to generate the data randomly
      int numberOfRecordsGenerated = 0;
      for ( ; numberOfRecordsGenerated < APPROX_RECORD_COUNT; ) {
        // generate random timestamp
        Timestamp start = Timestamp.valueOf(String.format("2023-01-01 00:00:00"));
        Timestamp end = Timestamp.valueOf(String.format("2023-01-01 00:59:59"));

        Timestamp time = randomTimeStampGenerator(start, end);

        numberOfRecordsGenerated = numberOfRecordsGenerated + generateRecordsForAParticularSec(time, writer);
      }

      writer.flush();
      writer.close();
      System.out.println("CSV file created successfully." + numberOfRecordsGenerated + " records generated.");

    } catch (IOException e) {
      System.err.println("Error creating CSV file: " + e.getMessage());
    }
  }

  /**
   * Generates records for a particular second with the given timestamp and writes them to a CSV file.
   *
   * @param time The timestamp for the particular second.
   * @param writer The FileWriter object to write the generated records to the CSV file.
   * @return The number of records generated for the particular second.
   */
  public static int generateRecordsForAParticularSec(Timestamp time, FileWriter writer) {
    int numberOfRecordsPerSecond = (int)generateRandomNumber(
            MIN_RECORD_COUNT_PER_SECOND,
            MAX_RECORD_COUNT_PER_SECOND
    );

    try {
      // generate records for this second
      for (int j = 0; j < numberOfRecordsPerSecond; j++) {
        double price = generateRandomPrice();
        long quantity = generateRandomQuantity();
        String sourceOfOrder = generateRandomOrderSource();

        // generate random timestamp for this particular second
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.add(Calendar.SECOND, 1);
        Timestamp end = new Timestamp(cal.getTime().getTime());
        Timestamp randTimeStamp = randomTimeStampGenerator(time, end);
        String unixTime = Long.toString(randTimeStamp.getTime() / 1000);

        String[] data = {
                unixTime,
                String.format("%.2f", price),
                Long.toString(quantity),
                sourceOfOrder
        };
        writer.write(String.join(",", data));
        writer.write("\n");
      }
    } catch (IOException e) {
      System.err.println("Error creating CSV file: " + e.getMessage());
    }

    return numberOfRecordsPerSecond;
  }

  /**
   * Returns a random order source from the predefined list of order sources.
   *
   * @return a randomly selected order source as a String
   */
  public static String generateRandomOrderSource() {
    // generate random integer between 0 and 6
    int rand = (int)generateRandomNumber(0, 6);

    return ORDER_SOURCE[rand];
  }

  /**
   *  Generates a random price
   *
   * @return a price as a double between 195 and 197
   */
  public static double generateRandomPrice() {
    return generateRandomNumber(195, 197);
  }

  /**
   * Generates a random quantity
   *
   * @return a random quantity of long type
   */
  public static long generateRandomQuantity() {
    return (long)generateRandomNumber(1, 999999);
  }

  /**
   * Generates a random number between the specified minimum and maximum values.
   *
   * @param min the minimum value
   * @param max the maximum value
   * @return a randomly generated number as a double
   */
  public static double generateRandomNumber(int min, int max) {
    return (Math.random() * (max - min + 1)) + min;
  }

  /**
   * Generates a random Timestamp between the specified start and end Timestamp values.
   *
   * @param startTime the starting Timestamp value
   * @param endTime the ending Timestamp value
   * @return a randomly generated Timestamp within the specified range
   */
  public static Timestamp randomTimeStampGenerator(Timestamp startTime, Timestamp endTime) {
    long offset = startTime.getTime();
    long end = endTime.getTime();

    long diff = end - offset;
    Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));

    return rand;
  }
}
