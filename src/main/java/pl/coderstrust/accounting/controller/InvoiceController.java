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
import java.util.Optional;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  // TODO - add validator - class which is checking if provided invoice is the correct one
  // (all required fields are filled etc)
  private InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public Collection<Invoice> getInvoices() throws IOException {
    return invoiceService.getInvoices();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getInvoice(@PathVariable int id) throws IOException {
    Optional<Invoice> invoiceOptional = invoiceService.getInvoices().stream()
        .filter(invoice -> invoice.getId().equals(id)).findAny();

    if (!invoiceOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(invoiceOptional.get());
  }

  @PostMapping
  public int saveInvoice(@RequestBody Invoice invoice) throws IOException {
    return invoiceService.saveInvoice(invoice);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateInvoice(@PathVariable int id, @RequestBody Invoice updatedInvoice)
      throws IOException, InvoiceNotFoundException {

    Optional<Invoice> invoiceOptional = invoiceService.getInvoices().stream()
        .filter(invoice -> invoice.getId().equals(id)).findAny();

    if (!invoiceOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    invoiceService.updateInvoice(id, updatedInvoice);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> removeInvoiceById(@PathVariable int id) throws InvoiceNotFoundException, IOException {

    Optional<Invoice> invoiceOptional = invoiceService.getInvoices().stream()
        .filter(invoice -> invoice.getId().equals(id)).findAny();

    if (!invoiceOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    invoiceService.removeInvoiceById(id);

    return ResponseEntity.ok().build();
  }

}
