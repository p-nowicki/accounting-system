package pl.coderstrust.accounting.database.impl.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.impl.helpers.FileHelper;
import pl.coderstrust.accounting.database.impl.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.database.impl.helpers.IdGenerator;
import pl.coderstrust.accounting.database.impl.helpers.ObjectMapperHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@ConditionalOnProperty(name = "pl.coderstrust.accounting.database", havingValue = "infile")
@Repository
public class InFileDatabase implements Database {

  private File databaseLocation;
  private FileInvoiceHelper fileInvoiceHelper = new FileInvoiceHelper(new FileHelper(),
      new ObjectMapperHelper()); // TODO implement dependency injection
  private IdGenerator idGenerator;
  private int id;// TODO reuse IdGenerator from multifileDatabase

  // TODO this class can be part some specific behaviour of multifileDatabase - just writing to single file
    InFileDatabase(@Value("databaselocation") String databaseLocation) {
    this.databaseLocation = new File(databaseLocation);
    id = getInvoices() // TODO reuse method from multifile (id generator)
        .stream()
        .max(Comparator.comparing(Invoice::getId))
        .orElse(new Invoice())
        .getId() + 1;
  }

  @Override
  public int saveInvoice(Invoice invoice) {
    invoice.setId(id);
    fileInvoiceHelper.saveInvoiceToFile(invoice, databaseLocation);
    return id++;
  }

  public List<Invoice> getInvoices() {
    return fileInvoiceHelper.readInvoicesFromFile(databaseLocation);
  }

  public void updateInvoice(int id, Invoice updatedInvoice) throws IOException {
    File tempFile = new File(databaseLocation + ".tmp");
    Collection<Invoice> invoices = getInvoices();
    invoices.removeIf(i -> i.getId() == id);
    invoices.add(updatedInvoice);
    for (Invoice invoice : invoices) {
      fileInvoiceHelper.saveInvoiceToFile(invoice, tempFile);
    }
    Path moveFrom = tempFile.toPath();
    Path target = databaseLocation.toPath();
    Files.move(moveFrom, target, StandardCopyOption.REPLACE_EXISTING);
  }

  @Override
  public Invoice getInvoiceById(int id) {
    List<Invoice> invoicesList = getInvoices();
    return invoicesList
        .stream()
        .filter(invoice -> invoice.getId().equals(id))
        .findAny()
        .orElse(null);
  }

  public void removeInvoiceById(int id) throws IOException {

    File tempFile = new File(databaseLocation + ".tmp");
    Collection<Invoice> invoices = getInvoices();
    invoices.removeIf(i -> i.getId() == id);
    for (Invoice invoice : invoices) {
      fileInvoiceHelper.saveInvoiceToFile(invoice, tempFile);
    }
    Path moveFrom = tempFile.toPath();
    Path target = databaseLocation.toPath();
    Files.move(moveFrom, target, StandardCopyOption.REPLACE_EXISTING);
  }
}