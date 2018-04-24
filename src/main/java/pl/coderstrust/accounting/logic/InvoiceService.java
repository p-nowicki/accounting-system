package pl.coderstrust.accounting.logic;

import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;

@Service
public class InvoiceService {

  private Database database;

  public InvoiceService(Database database) {
    this.database = database;
  }

  public int saveInvoice(Invoice invoice) {
    return database.saveInvoice(invoice);
  }

  public Collection<Invoice> getInvoices() {
    return database.getInvoices();
  }

  public void updateInvoice(Invoice invoice) {
    database.updateInvoice(invoice);
  }

  public void removeInvoiceById(int id) {
    database.removeInvoiceById(id);
  }

}