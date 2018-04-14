package pl.coderstrust.accounting.database.file;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;

public class InFileDatabase implements Database {

  @Override
  public void saveInvoice(Invoice invoice) {

  }

  @Override
  public Collection<Invoice> getInvoices() {
    return null;
  }

  @Override
  public void updateInvoice(Invoice invoice) {

  }

  @Override
  public void removeInvoiceById(int id) {

  }
}
