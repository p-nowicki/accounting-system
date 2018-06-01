package pl.coderstrust.accounting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.accounting.logic.InvoiceService;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
public class TaxCalculatorController {

  private InvoiceService invoiceService;
  private TaxCalculator taxCalculator;

  @Autowired
  public TaxCalculatorController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/invoices/costs")
  public BigDecimal getCosts() throws IOException {
    return taxCalculator.getCosts();
  }

  @GetMapping("/invoices/revenues")
  public BigDecimal getRevenues() throws IOException {
    return taxCalculator.getRevenues();
  }

  @GetMapping("/invoices/vatOutcome")
  public BigDecimal getVatOutcome() throws IOException {
    return taxCalculator.getVatOutcome();
  }

  @GetMapping("/invoices/vatIncome")
  public BigDecimal getVatIncome() throws IOException {
    return taxCalculator.getVatIncome();
  }

  @GetMapping("/invoices/vatDifference")
  public BigDecimal getVatDifference() throws IOException {
    return taxCalculator.getVatDifference();
  }
}
