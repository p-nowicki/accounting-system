package pl.coderstrust.accounting.database.impl.helpers;

import pl.coderstrust.accounting.database.impl.multifile.PathHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

  private AtomicInteger currentId;
  private FileInvoiceHelper fileInvoiceHelper;
  private PathHelper pathHelper;

  public IdGenerator(File databaseLocation, FileInvoiceHelper fileHelper,
      PathHelper pathGenerator) {
    this.fileInvoiceHelper = fileHelper;
    this.pathHelper = pathGenerator;
    currentId = new AtomicInteger(getCurrentId(databaseLocation).orElseGet(() -> 0));
  }

  public int generateNextId() {
    return currentId.getAndIncrement();
  }

  private OptionalInt getCurrentId(File databaseLocation) {
    return Files.isRegularFile(Paths.get(databaseLocation.getAbsolutePath()))
        ? getCurrentIdFromOneFile(databaseLocation)
        : getCurrentIdFromMultiFile(databaseLocation);
  }

  private OptionalInt getCurrentIdFromOneFile(File databaseLocation) {
    return fileInvoiceHelper
        .readInvoicesFromFile(databaseLocation)
        .stream()
        .mapToInt(Invoice::getId)
        .max();
  }

  private OptionalInt getCurrentIdFromMultiFile(File databaseLocation) {
    return pathHelper.listFiles(databaseLocation)
        .stream()
        .flatMap(file -> fileInvoiceHelper.readInvoicesFromFile(file).stream())
        .mapToInt(Invoice::getId)
        .max();
  }
}
