package pl.coderstrust.accounting.database.memory;


import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.IdGenerator;
import pl.coderstrust.accounting.database.ObjectMapperHelper;
import pl.coderstrust.accounting.helpers.FileHelper;
import pl.coderstrust.accounting.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class InFileDatabase implements Database {

  private File databaseLocation;
  private FileInvoiceHelper fileInvoiceHelper = new FileInvoiceHelper(new FileHelper(),
      new ObjectMapperHelper());
  private IdGenerator idGenerator;
  private int id;// TODO reuse IdHelper from multifileDatabase
  // TODO implement dependency injection

  public InFileDatabase(File databaseLocation,
      FileInvoiceHelper fileInvoiceHelper, IdGenerator idGenerator) {
    this.databaseLocation = databaseLocation;
    this.fileInvoiceHelper = fileInvoiceHelper;
    this.idGenerator = idGenerator;
  }

  // TODO this class can be part some specific behaviour of multifileDatabase - just writing to single file
  InFileDatabase(File databaseLocation) {
    this.databaseLocation = databaseLocation;
    id = getInvoices() // TODO reuse method from multifile
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