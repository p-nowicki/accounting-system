package pl.coderstrust.accounting.database.impl.multifile;

import pl.coderstrust.accounting.database.DatabaseForMultiFile;
import pl.coderstrust.accounting.database.IdGenerator;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MultiFileDatabase implements DatabaseForMultiFile {

  private FileHelper fileHelper;
  private PathHelper pathHelper;
  private IdGenerator idGenerator;
  private Map<Integer, String> fileCache;
  private String path;

  public MultiFileDatabase(String path, FileHelper fileHelper, PathHelper pathHelper, IdGenerator idGenerator)
      throws IOException {
    this.fileHelper = fileHelper;
    this.pathHelper = pathHelper;
    this.fileCache = initializeFileCache();
    this.idGenerator = idGenerator;
    this.path = path;
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
  public int updateInvoice(Invoice invoice) throws InvoiceNotFoundException, IOException {
    isInvoiceInDatabase(invoice.getId());
    deleteInvoice(invoice.getId());
    return saveInvoice(invoice);
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

  private void isInvoiceInDatabase(int id) throws InvoiceNotFoundException {
    if (!fileCache.containsKey(id)) {
      throw new InvoiceNotFoundException("InvoiceForMultifile not in database.");
    }
  }

  private Map<Integer, String> initializeFileCache() throws IOException {
    return getInvoices().stream().collect(Collectors.toMap(i -> i.getId(), i -> pathHelper.getPathToFile(i)));
  }
}