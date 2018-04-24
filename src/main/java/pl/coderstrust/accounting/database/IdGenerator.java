package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.database.impl.multifile.FileHelper;
import pl.coderstrust.accounting.database.impl.multifile.PathHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

  private AtomicInteger currentId;
  private FileHelper fileHelper;
  private PathHelper pathHelper;

  public IdGenerator(String path, FileHelper fileHelper, PathHelper pathGenerator) throws IOException {
    this.fileHelper = fileHelper;
    this.pathHelper = pathGenerator;
    currentId = new AtomicInteger(getCurrentId(path).orElseGet(() -> 0));
  }

  public int generateNextId() {
    return currentId.getAndIncrement();
  }

  private OptionalInt getCurrentId(String path) throws IOException {
    return Files.isRegularFile(Paths.get(path))
        ? getCurrentIdFromOneFile(path)
        : getCurrentIdFromMultiFile(path);
  }

  private OptionalInt getCurrentIdFromOneFile(String path) throws IOException {
    return fileHelper
        .readInvoicesFromFile(new File(path))
        .stream()
        .mapToInt(inv -> inv.getId())
        .max();
  }

  private OptionalInt getCurrentIdFromMultiFile(String path) throws IOException {
    List<Invoice> invoiceList = new ArrayList<>();
    List<File> files = pathHelper.listFiles(path);
    for (File file : files) {
      invoiceList.addAll(fileHelper.readInvoicesFromFile(file));
    }
    return invoiceList.stream()
        .mapToInt(inv -> inv.getId())
        .max();
  }
}
