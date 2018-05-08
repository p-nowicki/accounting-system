package pl.coderstrust.accounting.database.impl.helpers;

import pl.coderstrust.accounting.model.Invoice;
import java.io.File;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGeneratorForInFileDataBase {

  private AtomicInteger currentId;
  private FileInvoiceHelper fileInvoiceHelper;


  public IdGeneratorForInFileDataBase(File databaseLocation, FileInvoiceHelper fileHelper) {
    this.fileInvoiceHelper = fileHelper;
    currentId = new AtomicInteger(getCurrentIdFromFile(databaseLocation).orElseGet(() -> 1));
  }
  public int generateNextId() {
    return currentId.getAndIncrement();
  }

  private OptionalInt getCurrentIdFromFile(File databaseLocation) {
    return fileInvoiceHelper
        .readInvoicesFromFile(databaseLocation)
        .stream()
        .mapToInt(Invoice::getId)
        .max();
  }
}
