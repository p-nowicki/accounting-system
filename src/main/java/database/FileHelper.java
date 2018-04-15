package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

  public List<String> readLinesFromFile(String fileName) throws FileNotFoundException {
    List<String> line = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(String.valueOf(fileName)))) {
      while (scanner.hasNextLine()) {
        line.add(scanner.nextLine());
      }
    }

    return line;
  }

  public String procesLine(String line) {
    return line;
  }
}