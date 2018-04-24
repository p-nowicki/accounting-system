package pl.coderstrust.accounting.database.memory;

import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryDatabase implements Database {

  private final Map<Integer, Invoice> invoices = new HashMap<>();
  private int id = 0;

  public int saveInvoice(Invoice invoice) {
    invoice.setId(id);
    invoices.put(id, invoice);

    return id++;
  }

  public Collection<Invoice> getInvoices() {
    return invoices.values();
  }

  public int updateInvoice(Invoice invoice) {
    invoices.put(invoice.getId(), invoice);

    return id;
  }

  public void removeInvoiceById(int id) {
    invoices.remove(id);
  }
}
