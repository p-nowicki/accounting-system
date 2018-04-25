package pl.coderstrust.accounting.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

  public List<String> readLinesFromFile(String fileName) throws FileNotFoundException {
    List<String> line = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(fileName))) {
      while (scanner.hasNextLine()) {
        line.add(scanner.nextLine());
      }
    }
    return line;
  }

  public void writeFile(String fileName, String line) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
      bufferedWriter.write(String.valueOf(line));
      bufferedWriter.newLine();
    }
  }
}