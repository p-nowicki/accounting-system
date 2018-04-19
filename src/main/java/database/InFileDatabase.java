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

  private String fileName = "src/main/resources/file/newFile";
  private FileHelper fileHelper = new FileHelper();
  private int id = 1;

  public InFileDatabase(String fileName) {
    this.fileName = fileName;
  }

  InFileDatabase() {
  }

  public void saveInvoice(Invoice invoice) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    invoice.setId(generateId(id));
    fileHelper.writeFile(fileName, mapper.writeValueAsString(invoice));

  }

  public Collection<Invoice> getInvoices() throws FileNotFoundException {
    ObjectMapper mapper = new ObjectMapper();
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

  public void updateInvoice(Invoice invoice) {

  }

  public void removeInvoiceById(int id) throws IOException {
    List<String> list = new ArrayList<>(
        Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
    for (int i = 0; i < list.size(); i++) {
      if (id == i) {
        list.remove(id);
      }
    }
    Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
  }

  private int generateId(int id) throws IOException {
    List<String> list = new ArrayList<>(
        Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
    for (int i = 0; i < list.size(); i++) {
      id++;
    }
    return id;
  }
}
