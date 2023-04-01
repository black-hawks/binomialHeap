package stockExchange.inputGeneration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExternalMergeSort {

  // Divide the input file into smaller chunks, sort them, and merge them back
  public void sort(String inputFile, String outputFile) throws IOException {
    // Open the input CSV file for reading
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));

    // Create a list to hold the CSV entries
    List<String[]> entries = new ArrayList<>();

    // Read each line from the input file, parse it into a CSV entry, and add it to the list
    String line;
    while ((line = reader.readLine()) != null) {
      String[] entry = line.split(",");
      entries.add(entry);
    }

    // Sort the list of CSV entries using merge sort
    mergeSort(entries, 0, entries.size() - 1);

    // Open the output CSV file for writing
    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

    //writing the headers to the CSV File
    String[] headers = {"Timestamp", "Price", "Quantity", "Source of Order"};
    writer.write(String.join(",", headers));
    writer.newLine();

    // Write the sorted entries to the output file, one entry per line
    for (String[] entry : entries) {
      writer.write(String.join(",", entry));
      writer.newLine();
    }

    // Close the input and output files
    reader.close();
    writer.close();

  }

  public static void mergeSort(List<String[]> entries, int left, int right) {
    if (left < right) {
      int mid = left + (right - left) / 2;
      mergeSort(entries, left, mid);
      mergeSort(entries, mid + 1, right);
      merge(entries, left, mid, right);
    }
  }

   public static void merge(List<String[]> entries, int left, int mid, int right) {
     List<String[]> leftList = new ArrayList<>(entries.subList(left, mid + 1));
     List<String[]> rightList = new ArrayList<>(entries.subList(mid + 1, right + 1));

     int i = 0, j = 0, k = left;
     while (i < leftList.size() && j < rightList.size()) {
       // Change the index to the column you want to sort by
       if (leftList.get(i)[0].compareTo(rightList.get(j)[0]) <= 0) {
         entries.set(k++, leftList.get(i++));
       } else {
         entries.set(k++, rightList.get(j++));
       }
     }

     while (i < leftList.size()) {
       entries.set(k++, leftList.get(i++));
     }

     while (j < rightList.size()) {
       entries.set(k++, rightList.get(j++));
     }
   }
}
