package pl.coderstrust.accounting;

import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.logic.InvoiceService;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

@Service
public class TaxCalculator {

  private InvoiceService invoiceService;

  public TaxCalculator(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  public BigDecimal getCosts() throws IOException {
    Collection<Invoice> invoices = invoiceService.getInvoices();
    BigDecimal sum = BigDecimal.ZERO;
    for (Invoice invoice : invoices) {
      if (invoice.getBuyer().getNip() == MyCompany.NIP) {
        for (InvoiceEntry entry : invoice.getEntries()) {
          sum = sum.add(entry.getPrice());
        }
      }
    }
    return sum;
  }

  public BigDecimal getRevenues() throws IOException {
    Collection<Invoice> invoices = invoiceService.getInvoices();
    BigDecimal sum = BigDecimal.ZERO;
    for (Invoice invoice : invoices) {
      if (invoice.getSeller().getNip() == MyCompany.NIP) {
        for (InvoiceEntry entry : invoice.getEntries()) {
          sum = sum.add(entry.getPrice());
        }
      }
    }
    return sum;
  }

  public BigDecimal getVatOutcome() throws IOException {
    Collection<Invoice> invoices = invoiceService.getInvoices();
    BigDecimal sum = BigDecimal.ZERO;
    for (Invoice invoice : invoices) {
      if (invoice.getSeller().getNip() == MyCompany.NIP) {
        for (InvoiceEntry entry : invoice.getEntries()) {
          sum = sum.add(entry.getPrice().multiply(entry.getVatRate().getVatPercent()));
        }
      }
    }
    return sum;
  }

  public BigDecimal getVatIncome() throws IOException {
    Collection<Invoice> invoices = invoiceService.getInvoices();
    BigDecimal sum = BigDecimal.ZERO;
    for (Invoice invoice : invoices) {
      if (invoice.getBuyer().getNip() == MyCompany.NIP) {
        for (InvoiceEntry entry : invoice.getEntries()) {
          sum = sum.add(entry.getPrice().multiply(entry.getVatRate().getVatPercent()));
        }
      }
    }
    return sum;
  }

  public BigDecimal getVatDifference() throws IOException {
    BigDecimal vatDifference = getVatOutcome().subtract(getVatIncome());
    return vatDifference;
  }
}
