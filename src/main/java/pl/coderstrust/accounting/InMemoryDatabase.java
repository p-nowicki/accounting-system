package pl.coderstrust.accounting;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {

  private final Map<Integer, Invoice> invoces = new HashMap<>();

  public void saveInvoice(Invoice invoice) {
    invoices.put(invoice.getId(), invoice);
  }

  public Collection<Invoice> getInvoices() {

    return null;
  }

  public void updateInvoice(Invoice invoice) {
    invoices.put(invoice.getId);
  }

  public void removeInvoiceById(int id) {
    invoices.remove(id);
  }
}
