package database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InFileDatabase implements Database {

  private final Map<Integer, Invoice> invoces = new HashMap<>();

  public void saveInvoice(Invoice invoice) {
    invoces.put(invoice.getId(), invoice);
  }

  public Collection<Invoice> getInvoices() {

    return null;
  }

  @Override
  public void updateInvoice(Invoice invoice) {

  }

  public void removeInvoiceById(int id) {
    invoces.remove(id);
  }
}
