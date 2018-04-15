package database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InFileDatabase implements Database {

  private Map<Integer, Invoice> invoices = new HashMap<>();
  private FileHelper fileHelper;

  public InFileDatabase(FileHelper fileHelper) {
    this.fileHelper = fileHelper;
  }

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
