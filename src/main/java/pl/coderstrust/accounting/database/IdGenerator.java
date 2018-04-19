package pl.coderstrust.accounting.database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

  private static AtomicInteger currentId;
  private String currentIdFilePath;

  public IdGenerator(String currentIdFilePath) throws IOException {
    this.currentIdFilePath = currentIdFilePath;
    currentId = new AtomicInteger(getCurrentId(new File(currentIdFilePath)));
  }

  public int generateNextId() throws IOException {
    currentId.addAndGet(1);
    File file = new File(currentIdFilePath);
    Files.write(file.toPath(), String.valueOf(currentId).getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);
    return currentId.get();
  }

  private int getCurrentId(File file) throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }

    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNext()) {
        return sc.nextInt();
      }
    }
    return 0;
  }

}
