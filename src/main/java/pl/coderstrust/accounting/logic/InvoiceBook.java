package pl.coderstrust.accounting.logic;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.Collection;

public class InvoiceBook {

  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public void saveInvoice(Invoice invoice) throws IOException {
    database.saveInvoice(invoice);
  }

  public Collection<Invoice> getInvoices() throws IOException {
    return database.getInvoices();
  }

  public void updateInvoice(int id, Invoice invoice) throws IOException {
    database.updateInvoice(id, invoice);
  }

  public void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException {
    database.removeInvoiceById(id);
  }

}