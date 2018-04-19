package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.database.impl.multifile.Invoice;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DatabaseForMultiFile {

  int saveInvoice(Invoice invoice) throws IOException;

  Optional<Invoice> getInvoiceById(int id) throws IOException;

  int updateInvoice(Invoice invoice) throws InvoiceNotFoundException, IOException;

  List<Invoice> getInvoices() throws IOException;

  void deleteInvoice(int id) throws InvoiceNotFoundException, IOException;
}
