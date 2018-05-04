package pl.coderstrust.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  InvoiceValidator invoiceValidator = new InvoiceValidator();
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
  public ResponseEntity<?> saveInvoice(@RequestBody Invoice invoice) throws IOException {
    List<String> list = invoiceValidator.validate(invoice);
    if (!list.isEmpty()) {
      return ResponseEntity.badRequest().body(list);
    }
    int invoiceData = invoiceService.saveInvoice(invoice);
    return ResponseEntity.ok(invoiceData);
  }

  @PutMapping
  public ResponseEntity<?> updateInvoice(@PathVariable int id, @RequestBody Invoice invoice)
      throws IOException, InvoiceNotFoundException {
    List<String> list = invoiceValidator.validate(invoice);
    if (!list.isEmpty()) {
      return ResponseEntity.badRequest().body(list);
    }
    if (!list.contains(id)) {
      return ResponseEntity.notFound().build();
    }
    invoiceService.updateInvoice(id, invoice);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public void removeInvoiceById(int id) throws InvoiceNotFoundException, IOException {
    invoiceService.removeInvoiceById(id);
  }

}
