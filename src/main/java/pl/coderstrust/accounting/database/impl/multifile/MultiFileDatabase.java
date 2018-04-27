package pl.coderstrust.accounting.database.impl.multifile;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.IdGenerator;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultiFileDatabase implements Database {

  private FileHelper fileHelper;
  private PathHelper pathHelper;
  private IdGenerator idGenerator;
  private Map<Integer, String> fileCache;
  private String path;

  public MultiFileDatabase(String path, FileHelper fileHelper, PathHelper pathHelper, IdGenerator idGenerator)
      throws IOException {
    this.fileHelper = fileHelper;
    this.pathHelper = pathHelper;
    this.path = path;
    this.fileCache = initializeFileCache();
    this.idGenerator = idGenerator;
  }

  @Override
  public int saveInvoice(Invoice invoice) throws IOException {
    String pathToDirectory = pathHelper.getPathToDirectory(invoice);
    if (!pathHelper.pathExists(pathToDirectory)) {
      pathHelper.createFolder(pathToDirectory);
    }

    if (invoice.getId() == 0) {
      invoice.setId(idGenerator.generateNextId());
    }
    String filePath = pathHelper.getPathToFile(invoice);
    fileCache.put(invoice.getId(), filePath);

    if (pathHelper.pathExists(filePath)) {
      return fileHelper.saveInvoiceToFile(invoice, new File(filePath));
    }
    pathHelper.createFile(filePath);
    return fileHelper.saveInvoiceToFile(invoice, new File(filePath));
  }

  @Override
  public void updateInvoice(Invoice invoice) throws InvoiceNotFoundException, IOException {
    throwExceptionIfInvoiceNotInDatabase(invoice.getId());
    removeInvoiceById(invoice.getId());
    saveInvoice(invoice);
  }

  @Override
  public List<Invoice> getInvoices() throws IOException {
    List<Invoice> invoiceList = new ArrayList<>();
    List<File> files = pathHelper.listFiles(path);
    for (File file : files) {
      invoiceList.addAll(fileHelper.readInvoicesFromFile(file));
    }
    return invoiceList;
  }

  @Override
  public Invoice  getInvoiceById(int id) throws IOException {
    if (Objects.isNull(fileCache.get(id))) {
      return null;
    }

    File file = new File(fileCache.get(id));
    return fileHelper.readInvoicesFromFile(file)
        .stream()
        .filter(inv -> inv.getId() == id)
        .findFirst().get();
  }

  @Override
  public void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException {
    throwExceptionIfInvoiceNotInDatabase(id);

    File file = new File(fileCache.get(id));
    List<Invoice> invoiceList = fileHelper.readInvoicesFromFile(file);

    fileCache.remove(id);
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

  private void throwExceptionIfInvoiceNotInDatabase(int id) throws InvoiceNotFoundException {
    if (!fileCache.containsKey(id)) {
      throw new InvoiceNotFoundException("InvoiceForMultifile not in database.");
    }
  }

  private Map<Integer, String> initializeFileCache() throws IOException {
    return getInvoices().stream().collect(Collectors.toMap(i -> i.getId(), i -> pathHelper.getPathToFile(i)));
  }
}