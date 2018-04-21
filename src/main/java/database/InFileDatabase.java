package database;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InFileDatabase implements Database {

  private ObjectMapper mapper = new ObjectMapper();
  private String fileName;
  private FileHelper fileHelper = new FileHelper();
  private int id = 1;

  InFileDatabase(String fileName) {
    this.fileName = fileName;
  }

  public void saveInvoice(Invoice invoice) throws IOException {
    invoice.setId(id++);
    fileHelper.writeFile(fileName, mapper.writeValueAsString(invoice));
  }

  public Collection<Invoice> getInvoices() throws FileNotFoundException {
    return fileHelper.readLinesFromFile(fileName).stream().map(convertStringToInvoice(mapper))
        .collect(Collectors.toList());
  }

  private Function<String, Invoice> convertStringToInvoice(ObjectMapper mapper) {
    return invoice -> {
      try {
        return mapper.readValue(invoice, Invoice.class);
      } catch (IOException exception) {
        throw new RuntimeException(exception);
      }
    };
  }

  public void updateInvoice(int id, Invoice invoice) throws IOException {
    int newId = id - 1;
    List<String> list = new ArrayList<>(
        Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
    for (int i = 0; i < list.size(); ++i) {
      if (i == newId) {
        invoice.setId(id);
        list.set(i, mapper.writeValueAsString(invoice));
        break;
      }
    }
    Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
  }

  public void removeInvoiceById(int id) throws IOException {
    int newId = id - 1;
    if (id == 0) {
      throw new IllegalArgumentException(" Incorrect 'id' number : " + id);
    }
    List<String> list = new ArrayList<>(
        Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
    for (int i = 0; i <= list.size(); i++) {
      if (i == id) {
        list.remove(newId);
      }
      Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
    }
  }
}
