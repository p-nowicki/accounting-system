package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface Database {

  void saveInvoice(Invoice invoice) throws IOException;

  Collection<Invoice> getInvoices() throws FileNotFoundException;

  void updateInvoice(Invoice invoice) throws IOException;

  void removeInvoiceById(int id) throws IOException;

}
