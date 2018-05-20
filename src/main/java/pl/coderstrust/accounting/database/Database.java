package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface Database {

  int saveInvoice(Invoice invoice) throws IOException;

  Collection<Invoice> getInvoices() throws IOException;

  void updateInvoice(int id, Invoice invoice) throws InvoiceNotFoundException, IOException;

  Optional<Invoice> getInvoiceById(int id) throws IOException;

  void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException;
}
