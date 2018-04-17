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

public class FileHelperForMultiFileDatabase {

  private ObjectMapper mapper;
  private String currentIdFilePath;

  public FileHelperForMultiFileDatabase(String currentIdFilePath) {
    this.currentIdFilePath = currentIdFilePath;
    this.mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());
  }

  private String convertInvoiceToJson(Invoice invoice) throws JsonProcessingException {
    return mapper.writeValueAsString(invoice);
  }

  private Invoice convertJsonToInvoice(String invoiceAsString) throws IOException {
    return mapper.readValue(invoiceAsString, Invoice.class);
  }

  public int saveInvoiceToFile(Invoice invoice, File file) throws IOException {
    String invoiceAsString = convertInvoiceToJson(invoice);
    Files.write(file.toPath(), (invoiceAsString + System.lineSeparator()).getBytes(Charset.forName("UTF-8")),
        StandardOpenOption.APPEND);
    return invoice.getId();
  }

  public List<Invoice> readInvoicesFromFile(File file) throws IOException {
    List<Invoice> invoiceList = new ArrayList<>();
    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNext()) {
        Invoice invoice = convertJsonToInvoice(sc.nextLine());
        invoiceList.add(invoice);
      }
      return invoiceList;
    }
  }

  public int generateNextId() throws IOException {
    File file = new File(currentIdFilePath);
    int currentId = getCurrentId(file);
    currentId++;
    Files.write(file.toPath(), String.valueOf(currentId).getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);
    return currentId;
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
