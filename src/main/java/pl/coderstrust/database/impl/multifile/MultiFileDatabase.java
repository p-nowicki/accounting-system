package pl.coderstrust.database.impl.multifile;

import pl.coderstrust.exceptions.InvoiceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MultiFileDatabase implements DatabaseForMultiFile {

  private FileHelperForMultiFileDatabase fileHelper;
  private PathHelper pathHelper;
  private Map<Integer, String> fileCache;
  private String rootPath;

  public MultiFileDatabase(String rootPath, String currentIdFilePath) throws IOException {
    this.fileHelper = new FileHelperForMultiFileDatabase(currentIdFilePath);
    this.pathHelper = new PathHelper(rootPath);
    this.rootPath = rootPath;
    this.fileCache = initializeFileCache();
  }

  @Override
  public int saveInvoice(Invoice invoice) throws IOException {
    String pathToDirectory = pathHelper.getPathToDirectory(invoice);
    if (!pathHelper.pathExists(pathToDirectory)) {
      pathHelper.createFolder(pathToDirectory);
    }

    invoice.setId(fileHelper.generateNextId());
    String filePath = pathHelper.getPathToFile(invoice);
    fileCache.put(invoice.getId(), filePath);

    if (pathHelper.pathExists(filePath)) {
      return fileHelper.saveInvoiceToFile(invoice, new File(filePath));
    }
    pathHelper.createFile(filePath);
    return fileHelper.saveInvoiceToFile(invoice, new File(filePath));
  }

  @Override
  public int updateInvoice(Invoice invoice) throws InvoiceNotFoundException, IOException {
    isInvoiceInDatabase(invoice.getId());
    deleteInvoice(invoice.getId());
    invoice.setId(fileHelper.generateNextId());
    return saveInvoice(invoice);
  }

  @Override
  public List<Invoice> getInvoices() throws IOException {
    List<Invoice> invoiceList = new ArrayList<>();
    List<File> files = pathHelper.listFiles(rootPath);
    for (File file : files) {
      invoiceList.addAll(fileHelper.readInvoicesFromFile(file));
    }
    return invoiceList;
  }

  @Override
  public Optional<Invoice> getInvoiceById(int id) throws IOException {
    if (Objects.isNull(fileCache.get(id))) {
      return Optional.empty();
    }
    File file = new File(fileCache.get(id));
    return fileHelper.readInvoicesFromFile(file)
        .stream()
        .filter(inv -> inv.getId() == id)
        .findFirst();
  }

  @Override
  public void deleteInvoice(int id) throws InvoiceNotFoundException, IOException {
    isInvoiceInDatabase(id);

    List<Invoice> invoiceList = getInvoices();

    String filePath = fileCache.get(id);
    fileCache.remove(id);
    File file = new File(filePath);
    file.delete();

    invoiceList
        .stream()
        .filter(invoice -> invoice.getId() != id)
        .forEach(invoice -> {
          try {
            fileHelper.saveInvoiceToFile(invoice, file);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        });
  }

  private void isInvoiceInDatabase(int id) throws IOException, InvoiceNotFoundException {
    if (getInvoices().stream()
        .noneMatch(inv -> inv.getId() == id)) {
      throw new InvoiceNotFoundException("Invoice not in database.");
    }
  }

  private Map<Integer, String> initializeFileCache() throws IOException {
    return getInvoices().stream().collect(Collectors.toMap(i -> i.getId(), i -> pathHelper.getPathToFile(i)));
  }
}