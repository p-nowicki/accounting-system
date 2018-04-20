package pl.coderstrust.accounting.database.impl.multifile;

import pl.coderstrust.accounting.database.ObjectMapperHelper;

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

  public int saveInvoiceToFile(InvoiceForMultifile invoiceForMultifile, File file) throws IOException {
    String invoiceAsString = mapper.convertInvoiceToJson(invoiceForMultifile);
    Files.write(file.toPath(), (invoiceAsString + System.lineSeparator()).getBytes(Charset.forName("UTF-8")),
        StandardOpenOption.APPEND);
    return invoiceForMultifile.getId();
  }

  public List<InvoiceForMultifile> readInvoicesFromFile(File file) throws IOException {
    List<InvoiceForMultifile> invoiceForMultifileList = new ArrayList<>();
    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNext()) {
        InvoiceForMultifile invoiceForMultifile = mapper.convertJsonToInvoice(sc.nextLine());
        invoiceForMultifileList.add(invoiceForMultifile);
      }
      return invoiceForMultifileList;
    }
  }
}
