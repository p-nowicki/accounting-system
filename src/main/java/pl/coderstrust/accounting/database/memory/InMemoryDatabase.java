package pl.coderstrust.accounting.database.memory;

import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryDatabase implements Database {

  private final Map<Integer, Invoice> invoices = new HashMap<>();
  private int id = 0;

  public int saveInvoice(Invoice invoice) {
    invoice.setId(id);
    invoices.put(id++, invoice);
    return invoice.getId();
  }

  public Collection<Invoice> getInvoices() {
    return invoices.values();
  }

  public void updateInvoice(int id, Invoice invoice) {
    invoice.setId(id);
    invoices.put(id, invoice);
  }

  public void removeInvoiceById(int id) {
    invoices.remove(id);
  }

  @Override
  public Invoice getInvoiceById(int id) throws IOException {
    return invoices.get(id);
  }
}
