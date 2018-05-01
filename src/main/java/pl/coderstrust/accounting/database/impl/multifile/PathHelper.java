package pl.coderstrust.accounting.database.impl.multifile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PathHelper {

  private static final String EXTENSION_SUFFIX = ".json";

  private File rootPath;

  public PathHelper(File rootPath) {
    this.rootPath = rootPath;
  }

  private String getInvoiceYear(Invoice invoice) {
    return String.valueOf(invoice.getIssueDate().getYear());
  }

  private String getInvoiceMonth(Invoice invoice) {
    return invoice.getIssueDate().getMonth().toString().toLowerCase();
  }

  private String getInvoiceDay(Invoice invoice) {
    return String.valueOf(invoice.getIssueDate().getDayOfMonth());
  }

  public Path createFolder(String path) throws IOException {
    Path directory = Paths.get(path);
    return Files.createDirectories(directory);
  }

  public String getPathToDirectory(Invoice invoice) {
    return new StringBuilder(rootPath.getAbsolutePath())
        .append("/")
        .append(getInvoiceYear(invoice) + "/")
        .append(getInvoiceMonth(invoice) + "/")
        .toString();
  }

  public String getPathToFile(Invoice invoice) {
    return new StringBuilder(getPathToDirectory(invoice))
        .append(getInvoiceDay(invoice) + EXTENSION_SUFFIX)
        .toString();
  }

  public List<File> listFiles(File path) {
    return (List<File>) FileUtils
        .listFiles(path, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
  }

  public boolean pathExists(String path) {
    return Files.exists(Paths.get(path));
  }

}
