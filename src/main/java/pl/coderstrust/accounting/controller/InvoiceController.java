package pl.coderstrust.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.logic.InvoiceService;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  private InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public Collection<Invoice> getInvoices() throws IOException {
    return invoiceService.getInvoices();
  }

  @PostMapping
  public int saveInvoice(@RequestBody Invoice invoice) throws IOException {
    return invoiceService.saveInvoice(invoice);
  }

  @PutMapping
  public int updateInvoice(@RequestBody Invoice invoice) throws IOException {
    return invoiceService.saveInvoice(invoice);
  }

  @DeleteMapping
  public void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException {
    invoiceService.removeInvoiceById(id);
  }

}
