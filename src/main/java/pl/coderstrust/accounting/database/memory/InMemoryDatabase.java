package pl.coderstrust.accounting.database.memory;

import pl.coderstrust.accounting.logic.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {

  private final Map<Integer, Invoice> invoices = new HashMap<>();
  private int id = 0;

  public int saveInvoice(Invoice invoice) {
    invoice.setId(id);
    invoices.put(id++, invoice);
    return id;
  }

  public Collection<Invoice> getInvoices() {
    return invoices.values();
  }

  @Override
  public void updateInvoice(int id, Invoice invoice) {

  }

  public void updateInvoice(Invoice invoice) {
    invoices.put(invoice.getId(), invoice);
  }

  public void removeInvoiceById(int id) {
    invoices.remove(id);
  }
}
