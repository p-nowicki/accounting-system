package pl.coderstrust.accounting.logic;

import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;

import java.util.Collection;

@Service
public class InvoiceService {

  private Database database;

  public InvoiceService(Database database) {
    this.database = database;
  }

  public int saveInvoice(Invoice invoice) throws IOException {
    return database.saveInvoice(invoice);
  }

  public Collection<Invoice> getInvoices() throws IOException {
    return database.getInvoices();
  }

  public void updateInvoice(int id, Invoice invoice) throws IOException, InvoiceNotFoundException {
    database.updateInvoice(id, invoice);
  }

  public void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException {
    database.removeInvoiceById(id);
  }
}