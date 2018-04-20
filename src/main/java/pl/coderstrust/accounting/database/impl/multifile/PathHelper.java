package pl.coderstrust.accounting.database.impl.multifile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PathHelper {

  private static final String EXTENSION_SUFFIX = ".json";

  private String rootPath;

  public PathHelper(String rootPath) {
    this.rootPath = rootPath;
  }

  private String getInvoiceYear(InvoiceForMultifile invoiceForMultifile) {
    return String.valueOf(invoiceForMultifile.getIssueDate().getYear());
  }

  private String getInvoiceMonth(InvoiceForMultifile invoiceForMultifile) {
    return invoiceForMultifile.getIssueDate().getMonth().toString().toLowerCase();
  }

  private String getInvoiceDay(InvoiceForMultifile invoiceForMultifile) {
    return String.valueOf(invoiceForMultifile.getIssueDate().getDayOfMonth());
  }

  public Path createFolder(String path) throws IOException {
    Path directory = Paths.get(path);
    return Files.createDirectories(directory);
  }

  public boolean createFile(String path) throws IOException {
    File file = new File(path);
    return file.createNewFile();
  }

  public String getPathToDirectory(InvoiceForMultifile invoiceForMultifile) {
    return new StringBuilder(rootPath)
        .append(getInvoiceYear(invoiceForMultifile) + "/")
        .append(getInvoiceMonth(invoiceForMultifile) + "/")
        .toString();
  }

  public String getPathToFile(InvoiceForMultifile invoiceForMultifile) {
    return new StringBuilder(getPathToDirectory(invoiceForMultifile))
        .append(getInvoiceDay(invoiceForMultifile) + EXTENSION_SUFFIX)
        .toString();
  }

  public List<File> listFiles(String path) {
    File source = new File(path);
    return (List<File>) FileUtils.listFiles(source, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
  }

  public boolean pathExists(String path) {
    return Files.exists(Paths.get(path));
  }

}
