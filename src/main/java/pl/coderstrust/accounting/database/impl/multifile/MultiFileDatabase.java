package pl.coderstrust.accounting.database.impl.multifile;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.impl.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.database.impl.helpers.IdGenerator;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "pl.coderstrust.accounting.database", havingValue = "multifile")
@Repository
public class MultiFileDatabase implements Database {

  private FileInvoiceHelper fileInvoiceHelper;
  private PathHelper pathHelper;
  private IdGenerator idGenerator;
  private Map<Integer, String> fileCache;
  private File databaseLocation;

  public MultiFileDatabase(File databaseLocation, FileInvoiceHelper fileInvoiceHelper,
      PathHelper pathHelper,
      IdGenerator idGenerator) throws IOException {
    this.fileInvoiceHelper = fileInvoiceHelper;
    this.pathHelper = pathHelper;
    this.databaseLocation = databaseLocation;
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

    fileInvoiceHelper.saveInvoiceToFile(invoice, new File(filePath));
    return invoice.getId();
  }

  @Override
  public void updateInvoice(int id, Invoice invoice) throws InvoiceNotFoundException, IOException {
    throwExceptionIfInvoiceNotInDatabase(id);
    removeInvoiceById(id);
    invoice.setId(id);
    saveInvoice(invoice);
  }

  @Override
  public List<Invoice> getInvoices() {
    List<Invoice> invoiceList = new ArrayList<>();
    List<File> files = pathHelper.listFiles(databaseLocation);
    for (File file : files) {
      invoiceList.addAll(fileInvoiceHelper.readInvoicesFromFile(file));
    }
    return invoiceList;
  }

  @Override
  public Invoice getInvoiceById(int id) {
    if (Objects.isNull(fileCache.get(id))) {
      return null;
    }

    File file = new File(fileCache.get(id));
    return fileInvoiceHelper.readInvoicesFromFile(file)
        .stream()
        .filter(invoice -> invoice.getId() == id)
        .findFirst().get();
  }

  @Override
  public void removeInvoiceById(int id) throws InvoiceNotFoundException {
    throwExceptionIfInvoiceNotInDatabase(id);

    File file = new File(fileCache.get(id));
    List<Invoice> invoiceList = fileInvoiceHelper.readInvoicesFromFile(file);

    fileCache.remove(id);
    file.delete();

    invoiceList
        .stream()
        .filter(invoice -> invoice.getId() != id)
        .forEach(invoice -> fileInvoiceHelper.saveInvoiceToFile(invoice, file));
  }

  private void throwExceptionIfInvoiceNotInDatabase(int id) throws InvoiceNotFoundException {
    if (!fileCache.containsKey(id)) {
      throw new InvoiceNotFoundException("InvoiceForMultifile not in database.");
    }
  }

  private Map<Integer, String> initializeFileCache() throws IOException {
    return getInvoices().stream()
        .collect(Collectors.toMap(i -> i.getId(), i -> pathHelper.getPathToFile(i)));
  }
}