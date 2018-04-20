package pl.coderstrust.database.impl.multifile;

import pl.coderstrust.exceptions.InvoiceNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface DatabaseForMultiFile {

  int saveInvoice(Invoice invoice) throws IOException;

  Optional<Invoice> getInvoiceById(int id) throws IOException;

  int updateInvoice(Invoice invoice) throws InvoiceNotFoundException, IOException;

  List<Invoice> getInvoices() throws IOException;

  default List<Invoice> getInvoicesWithinDateRange(LocalDate fromDate, LocalDate toDate) throws IOException {
    return getInvoices().stream()
        .filter(invoice -> invoice.getIssueDate().isAfter(fromDate) && invoice.getIssueDate().isBefore(toDate))
        .collect(Collectors.toList());
  }

  void deleteInvoice(int id) throws InvoiceNotFoundException, IOException;
}
