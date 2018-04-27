package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.Collection;

public interface Database {

  int saveInvoice(Invoice invoice) throws IOException;

  Collection<Invoice> getInvoices() throws IOException;

  int updateInvoice(int id, Invoice invoice) throws InvoiceNotFoundException, IOException;

  Invoice getInvoiceById(int id) throws IOException;

  void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException;
}
