package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.database.impl.multifile.PathHelper;
import pl.coderstrust.accounting.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

  private AtomicInteger currentId;
  private FileInvoiceHelper fileInvoiceHelper;
  private PathHelper pathHelper;

  public IdGenerator(File databaseLocation, FileInvoiceHelper fileHelper, PathHelper pathGenerator)
      throws IOException {
    this.fileInvoiceHelper = fileHelper;
    this.pathHelper = pathGenerator;
    currentId = new AtomicInteger(getCurrentId(databaseLocation).orElseGet(() -> 0));
  }

  public int generateNextId() {
    return currentId.getAndIncrement();
  }

  private OptionalInt getCurrentId(File databaseLocation) throws IOException {
    return Files.isRegularFile(Paths.get(databaseLocation.getAbsolutePath()))
        ? getCurrentIdFromOneFile(databaseLocation)
        : getCurrentIdFromMultiFile(databaseLocation);
  }

  private OptionalInt getCurrentIdFromOneFile(File databaseLocation) throws IOException {
    return fileInvoiceHelper
        .readInvoicesFromFile(databaseLocation)
        .stream()
        .mapToInt(Invoice::getId)
        .max();
  }

  private OptionalInt getCurrentIdFromMultiFile(File databaseLocation) throws IOException {
    return pathHelper.listFiles(databaseLocation)
        .stream()
        .flatMap(file -> fileInvoiceHelper.readInvoicesFromFile(file).stream())
        .mapToInt(Invoice::getId)
        .max();
  }
}
