package inputGeneration;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class DataGenerator {
  final static String CLIENT_ORDER = "ClientOrder";
  final static String FOREIGN_INVESTOR_ORDER = "ForeignInvestorOrder";
  final static String MARKET_CONTROLLER_ORDER = "MarketControllerOrder";
  final static String MUTUAL_FUND_ORDER = "MutualFundOrder";
  final static String ISSUER_ORDER = "IssuerOrder";
  final static String PROFESSIONAL_ORDER = "ProfessionalOrder";
  final static String INSIDER_ORDER = "InsiderOrder";

  final static String ORDER_SOURCE[] = {
          CLIENT_ORDER,
          FOREIGN_INVESTOR_ORDER,
          MARKET_CONTROLLER_ORDER,
          MUTUAL_FUND_ORDER,
          ISSUER_ORDER,
          PROFESSIONAL_ORDER,
          INSIDER_ORDER
  };

  final static int APPROX_RECORD_COUNT = 600000;
  final static int MIN_RECORD_COUNT_PER_SECOND = 5000;
  final static int MAX_RECORD_COUNT_PER_SECOND = 10000;

  public static void main(String args[]) {
    String csvFile = "DataFile.csv";

    try(FileWriter writer = new FileWriter(csvFile)) {
      // code to generate the data randomly
      for (int currentRecordCount = 0; currentRecordCount < APPROX_RECORD_COUNT; ) {

        int randomSecond = (int)generateRandomNumber(0, 59);
        int numberOfRecordsPerSecond = (int)generateRandomNumber(
                MIN_RECORD_COUNT_PER_SECOND,
                MAX_RECORD_COUNT_PER_SECOND
        );

        // generate records for this second
        for (int j = 0; j < numberOfRecordsPerSecond; j++) {
          double price = generateRandomPrice();
          long quantity = generateRandomQuantity();
          String sourceOfOrder = generateRandomOrderSource();
          Timestamp randTimeStamp = randomTimeStampGenerator(randomSecond);

          String[] data = {
                  randTimeStamp.toString(),
                  String.format("%.2f", price),
                  Long.toString(quantity),
                  sourceOfOrder
          };
          writer.write(String.join(",", data));
          writer.write("\n");
        }
        currentRecordCount = currentRecordCount + numberOfRecordsPerSecond;
      }

      System.out.println("CSV file created successfully.");

    } catch (IOException e) {
      System.err.println("Error creating CSV file: " + e.getMessage());
    }
  }

  public static String generateRandomOrderSource() {
    // generate random integer between 0 and 6
    int rand = (int)generateRandomNumber(0, 6);

    return ORDER_SOURCE[rand];
  }

  public static double generateRandomPrice() {
    return generateRandomNumber(1, 999999);
  }

  public static long generateRandomQuantity() {
    return (long)generateRandomNumber(1, 999999);
  }

  public static double generateRandomNumber(int min, int max) {
    return (Math.random() * (max - min + 1)) + min;
  }

  public static Timestamp randomTimeStampGenerator(int second) {
    long offset = Timestamp.valueOf(String.format("2023-01-01 00:01:%02d", second)).getTime();
    long end = Timestamp.valueOf(String.format("2023-01-01 00:01:%02d", (second + 1))).getTime();

    long diff = end - offset;
    Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));

    return rand;
  }
}