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

  public List<String> readLinesFromFile(File file) {
    List<String> line = new ArrayList<>();

    if (!file.exists()) {
      return new ArrayList<>();
    }

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        line.add(scanner.nextLine());
      }
    } catch (FileNotFoundException ex) {
      // ignore - we checked before if file exists
      throw new RuntimeException(ex);
    }

    return line;
  }

  public void writeFile(File file, String line) { // TODO writeLineToFile
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
      bufferedWriter.write(String.valueOf(line));
      bufferedWriter.newLine();
      bufferedWriter.flush();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
