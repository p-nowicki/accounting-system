package database;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    FileHelper fileHelper = new FileHelper();
    fileHelper.writeFile("01/10/1988", "george ");
    String reader = String
        .valueOf(fileHelper.readLinesFromFile("src/main/resources/file/newFile.txt"));
    System.out.println(reader);
  }
}
