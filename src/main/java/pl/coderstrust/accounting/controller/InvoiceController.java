package pl.coderstrust.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.accounting.logic.InvoiceService;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  private InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceBook) {
    this.invoiceService = invoiceBook;
  }

  @GetMapping
  public Collection<Invoice> getInvoices() {
    return invoiceService.getInvoices();
  }

  @PostMapping
  public int saveInvoice(@RequestBody Invoice invoice) {
    return invoiceService.saveInvoice(invoice);
  }

  @PutMapping
  public int updateInvoice(@RequestBody Invoice invoice) {
    return invoiceService.saveInvoice(invoice);
  }

  @DeleteMapping
  public void removeInvoiceById(int id) {
    invoiceService.removeInvoiceById(id);
  }

}
