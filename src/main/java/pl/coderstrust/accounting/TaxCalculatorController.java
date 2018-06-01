package pl.coderstrust.accounting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.accounting.logic.InvoiceService;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

@RestController
public class TaxCalculatorController {

  private InvoiceService invoiceService;

  @Autowired
  public TaxCalculatorController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/invoices/costs")
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

  @GetMapping("/invoices/revenues")
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

  @GetMapping("/invoices/vatOutcome")
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

  @GetMapping("/invoices/vatIncome")
  public BigDecimal getVatIncome() throws IOException {
    Collection<Invoice> invoices = invoiceService.getInvoices();
    BigDecimal sum = BigDecimal.ZERO;
    for (Invoice invoice : invoices) {
      if (invoice.getSeller().getNip() != MyCompany.NIP) {
        for (InvoiceEntry entry : invoice.getEntries()) {
          sum = sum.add(entry.getPrice().multiply(entry.getVatRate().getVatPercent()));
        }
      }
    }
    return sum;
  }

  @GetMapping("/invoices/vatDifference")
  public BigDecimal vatDifference() throws IOException {
    BigDecimal vatDifference = getVatOutcome().subtract(getVatIncome());
    return vatDifference;
  }
}
