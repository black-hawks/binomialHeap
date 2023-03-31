package inputGeneration;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

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

  final static int APPROX_RECORD_COUNT = 6000000;
  final static int MIN_RECORD_COUNT_PER_SECOND = 5000;
  final static int MAX_RECORD_COUNT_PER_SECOND = 10000;

  final static int TOTAL_SECONDS = 60 * 60; // one hour

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

  public static String generateRandomOrderSource() {
    // generate random integer between 0 and 6
    int rand = (int)generateRandomNumber(0, 6);

    return ORDER_SOURCE[rand];
  }

  public static double generateRandomPrice() {
    return generateRandomNumber(195, 197);
  }

  public static long generateRandomQuantity() {
    return (long)generateRandomNumber(1, 999999);
  }

  public static double generateRandomNumber(int min, int max) {
    return (Math.random() * (max - min + 1)) + min;
  }

  public static Timestamp randomTimeStampGenerator(Timestamp startTime, Timestamp endTime) {
    long offset = startTime.getTime();
    long end = endTime.getTime();

    long diff = end - offset;
    Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));

    return rand;
  }
}
