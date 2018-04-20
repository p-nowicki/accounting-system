package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.database.impl.multifile.FileHelper;
import pl.coderstrust.accounting.database.impl.multifile.Invoice;
import pl.coderstrust.accounting.database.impl.multifile.PathHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

  private static AtomicInteger currentId;

  public IdGenerator(String path) throws IOException {
    currentId = new AtomicInteger(getCurrentId(path).orElseGet(() -> 0));
  }

  public int generateNextId() {
    currentId.incrementAndGet();
    return currentId.get();
  }

  private OptionalInt getCurrentId(String path) throws IOException {
    return Files.isRegularFile(Paths.get(path))
        ? getCurrentIdFromOneFile(path)
        : getCurrentIdFromMultiFile(path);
  }

  private OptionalInt getCurrentIdFromOneFile(String path) throws IOException {
    return new FileHelper()
        .readInvoicesFromFile(new File(path))
        .stream()
        .mapToInt(inv -> inv.getId())
        .max();
  }

  private OptionalInt getCurrentIdFromMultiFile(String path) throws IOException {
    List<Invoice> invoiceList = new ArrayList<>();
    List<File> files = new PathHelper(path).listFiles(path);
    for (File file : files) {
      invoiceList.addAll(new FileHelper().readInvoicesFromFile(file));
    }
    return invoiceList.stream()
        .mapToInt(inv -> inv.getId())
        .max();
  }
}
