package pl.coderstrust.accounting.database.impl.multifile;

import pl.coderstrust.accounting.database.ObjectMapperHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

  private ObjectMapperHelper mapper;

  public FileHelper() {
    this.mapper = new ObjectMapperHelper();
  }

  public int saveInvoiceToFile(Invoice invoice, File file) throws IOException {
    String invoiceAsString = mapper.convertInvoiceToJson(invoice);
    Files.write(file.toPath(), (invoiceAsString + System.lineSeparator()).getBytes(Charset.forName("UTF-8")),
        StandardOpenOption.APPEND);
    return invoice.getId();
  }

  public List<Invoice> readInvoicesFromFile(File file) throws IOException {
    List<Invoice> invoiceList = new ArrayList<>();
    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNext()) {
        Invoice invoice = mapper.convertJsonToInvoice(sc.nextLine());
        invoiceList.add(invoice);
      }
      return invoiceList;
    }
  }
}
