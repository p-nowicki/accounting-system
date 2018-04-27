package pl.coderstrust.accounting.database.memory;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.ObjectMapperHelper;
import pl.coderstrust.accounting.helpers.FileHelper;
import pl.coderstrust.accounting.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class InFileDatabase implements Database {

  private File databaseLocation;
  private FileInvoiceHelper fileInvoiceHelper = new FileInvoiceHelper(new FileHelper(),
      new ObjectMapperHelper());
  private int id; // TODO reuse IdHelper from multifileDatabase
  // TODO implement dependency injection

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

  public void updateInvoice(int id, Invoice invoice) {
    invoice.setId(id);

    List<Invoice> list = getInvoices();
    databaseLocation.delete();
    // TODO what will happen if application crash now?
    // add handling of temporary file we write to and then replace with origiinal

    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i).getId().equals(id)) {
        list.set(i, invoice);
      }
      fileInvoiceHelper.saveInvoiceToFile(list.get(i), databaseLocation);
    }
  }

  @Override
  public Invoice getInvoiceById(int id) throws IOException {
    return null; // TODO implement
  }

  public void removeInvoiceById(int id) throws IOException {
    List<Invoice> list = getInvoices();
    databaseLocation.delete();
    // TODO what will happen if application crash now?
    // add handling of temporary file we write to and then replace with origiinal
    // TODO - can we make those 2 methods nicer? :)

    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i).getId().equals(id)) {
        continue;
      }
      fileInvoiceHelper.saveInvoiceToFile(list.get(i), databaseLocation);
    }
  }
}