package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.database.impl.multifile.InvoiceForMultifile;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DatabaseForMultiFile {

  int saveInvoice(InvoiceForMultifile invoiceForMultifile) throws IOException;

  Optional<InvoiceForMultifile> getInvoiceById(int id) throws IOException;

  int updateInvoice(InvoiceForMultifile invoiceForMultifile) throws InvoiceNotFoundException, IOException;

  List<InvoiceForMultifile> getInvoices() throws IOException;

  void deleteInvoice(int id) throws InvoiceNotFoundException, IOException;
}
