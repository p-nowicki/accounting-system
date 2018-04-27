package pl.coderstrust.accounting.database.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.coderstrust.accounting.logic.Database;
import pl.coderstrust.accounting.logic.FileHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InFileDatabase implements Database {

  private ObjectMapper mapper = new ObjectMapper();
  private String fileName;
  private FileHelper fileHelper = new FileHelper();
  private int id;

  InFileDatabase(String fileName) throws IOException {
    this.fileName = fileName;
    id = getInvoices().stream().max(Comparator.comparing(v -> v.getId())).orElse(new Invoice())
        .getId() + 1;
  }

  @Override
  public int saveInvoice(Invoice invoice) throws IOException {
    invoice.setId(id++);
    fileHelper.writeFile(fileName, mapper.writeValueAsString(invoice));
    return id;
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
    List<String> list = new ArrayList<>(
        Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i).contains("" + id)) {
        invoice.setId(id);
        list.set(i, mapper.writeValueAsString(invoice));
        break;
      }
    }
    Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
  }

  public void removeInvoiceById(int id) throws IOException {
    List<String> list = new ArrayList<>(
        Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).contains("" + id)) {
        list.remove(id);
      }
      Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
    }
  }
}