package database;

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

  public void writeFile(String id, String file) {
    BufferedWriter writer = null;
    try {

      File logFile = new File("src/main/resources/file/newFile.txt");
      System.out.println(logFile.getCanonicalPath());

      writer = new BufferedWriter(new FileWriter(logFile, true));
      writer.write(" Id number: " + id + " File name: " + file);

    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
  }
}