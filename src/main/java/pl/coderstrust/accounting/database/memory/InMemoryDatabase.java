package pl.coderstrust.accounting;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {

  private final Map<Integer, Invoice> invoices = new HashMap<>();

  public void saveInvoice(Invoice invoice) {
    invoices.put(invoice.getId(), invoice);
  }

  public Collection<Invoice> getInvoices() {
    return invoices.values();
  }

  public void updateInvoice(Invoice invoice) {
    invoices.put(invoice.getId(), invoice);
  }

  public void removeInvoiceById(int id) {
    invoices.remove(id);
  }
}
