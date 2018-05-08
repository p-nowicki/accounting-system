package pl.coderstrust.accounting.database.impl.file;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.impl.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.database.impl.helpers.IdGenerator;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class InFileDatabase implements Database {

  private File databaseLocation;
  private FileInvoiceHelper fileInvoiceHelper;
  private IdGenerator idGenerator;

  public InFileDatabase(File databaseLocation, FileInvoiceHelper fileInvoiceHelper, IdGenerator idGenerator) {
    this.databaseLocation = databaseLocation;
    this.fileInvoiceHelper = fileInvoiceHelper;
    this.idGenerator = idGenerator;
  }

  @Override
  public int saveInvoice(Invoice invoice) {
    if (invoice.getId() == 0) {
      invoice.setId(idGenerator.generateNextId());
    }
    fileInvoiceHelper.saveInvoiceToFile(invoice, databaseLocation);
    return invoice.getId();
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
  public Optional<Invoice> getInvoiceById(int id) {
    return fileInvoiceHelper.readInvoicesFromFile(databaseLocation)
        .stream()
        .filter(invoice -> invoice.getId() == id)
        .findFirst();
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
