package pl.coderstrust.accounting.logic;

import pl.coderstrust.accounting.model.Invoice;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.Collection;

public interface Database {

  int saveInvoice(Invoice invoice) throws IOException;

  Collection<Invoice> getInvoices() throws FileNotFoundException;

  void updateInvoice(int id, Invoice invoice) throws IOException;

  void removeInvoiceById(int id) throws IOException;

}
