package pl.coderstrust.accounting.database.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Primary
public class HibernateDatabase implements Database {

  private InvoiceRepository invoiceRepository;
  private CompanyRepository companyRepository;
  private InvoiceEntryRepository invoiceEntryRepository;

  @Autowired
  public HibernateDatabase(InvoiceRepository invoiceRepository, CompanyRepository companyRepository,
      InvoiceEntryRepository invoiceEntryRepository) {
    this.invoiceRepository = invoiceRepository;
    this.companyRepository = companyRepository;
    this.invoiceEntryRepository = invoiceEntryRepository;
  }

  @Override
  public int saveInvoice(Invoice invoice) throws IOException {
    addCompanyIfNotExists(invoice.getSeller());
    addCompanyIfNotExists(invoice.getBuyer());
    return invoiceRepository.save(invoice).getId();
  }

  @Override
  public Collection<Invoice> getInvoices() throws IOException {
    return StreamSupport.stream(invoiceRepository
        .findAll()
        .spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public void updateInvoice(int id, Invoice invoice) throws InvoiceNotFoundException, IOException {}

  @Override
  public Invoice getInvoiceById(int id) throws IOException {
    return invoiceRepository.findById(id).get();
  }

  @Override
  public void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException {
    ifInvoiceNotFoundThrowException(id);

    invoiceEntryRepository.deleteAll(invoiceRepository.findById(id).get().getEntries());
    invoiceRepository.deleteById(id);
  }

  private void ifInvoiceNotFoundThrowException(int id) throws InvoiceNotFoundException {
    if (!invoiceRepository.findById(id).isPresent()) {
      throw new InvoiceNotFoundException("Invoice not in database.");
    }
  }

  private void addCompanyIfNotExists(Company company) {
    if (!companyRepository.findByNip(company.getNip()).isPresent()) {
      companyRepository.save(company);
    }
  }
}
