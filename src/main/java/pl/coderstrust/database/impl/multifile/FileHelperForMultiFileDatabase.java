package pl.coderstrust.database.impl.multifile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FileHelperForMultiFileDatabase {

  private ObjectMapper mapper;
  private String currentIdFilePath;
  private static AtomicInteger currentId;

  public FileHelperForMultiFileDatabase(String currentIdFilePath) throws IOException {
    this.currentIdFilePath = currentIdFilePath;
    this.mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());
    currentId = new AtomicInteger(getCurrentId(new File(currentIdFilePath)));
  }

  private String convertInvoiceToJson(Invoice invoice) throws JsonProcessingException {
    return "";
  }

  private Invoice convertJsonToInvoice(String invoiceAsString) throws IOException {
    return null;
  }

  public int saveInvoiceToFile(Invoice invoice, File file) throws IOException {
    return -1;
  }

  public List<Invoice> readInvoicesFromFile(File file) throws IOException {
      return new ArrayList<>();
  }

  public int generateNextId() throws IOException {
    File file = new File(currentIdFilePath);
    currentId.addAndGet(1);
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
