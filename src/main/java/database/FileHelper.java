package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

  public List<String> saveInvoice(int id) throws FileNotFoundException {
    List<String> line = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(String.valueOf(id)))) {
      while (scanner.hasNextLine()) {
        line.add(scanner.nextLine());
      }
    }

    return line;
  }

  public void writeLinesToFile(List<String> resultLines, String resultFileName) throws IOException {

    Path file = Paths.get(resultFileName);
    Files.write(file, resultLines, Charset.forName("UTF-8"));
  }

}
