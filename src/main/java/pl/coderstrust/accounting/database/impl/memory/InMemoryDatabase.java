package pl.coderstrust.accounting.database.impl.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(name = "pl.coderstrust.accounting.database", havingValue = "inmemory")
@Repository
public class InMemoryDatabase implements Database {

  private final Map<Integer, Invoice> invoices = new HashMap<>();
  private int id = 1;

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
  public Invoice getInvoiceById(int id) {
    return invoices.get(id);
  }
}
