package database;

import java.util.Collection;

public interface Database {

  void saveInvoice(Invoice invoice);

  Collection<Invoice> getInvoices();

  void updateInvoice(Invoice invoice);

  void removeInvoiceById(int id);
}
