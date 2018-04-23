package pl.coderstrust.accounting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.accounting.database.memory.InMemoryDatabase;
import pl.coderstrust.accounting.logic.InvoiceBook;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  private InvoiceBook invoiceBook = new InvoiceBook(new InMemoryDatabase());

  @GetMapping
  public Collection<Invoice> getInvoices(){
    return invoiceBook.getInvoices();
  }

  @PostMapping
  public void saveInvoice(){

  }

}
