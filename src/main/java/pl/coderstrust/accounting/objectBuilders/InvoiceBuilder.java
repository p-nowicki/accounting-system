package pl.coderstrust.accounting.objectBuilders;

import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.time.LocalDate;
import java.util.List;

public class InvoiceBuilder {

  private Invoice invoice;

  public InvoiceBuilder() {
    invoice = new Invoice();
  }

  public InvoiceBuilder id(int id) {
    invoice.setId(id);
    return this;
  }

  public InvoiceBuilder number(String number) {
    invoice.setNumber(number);
    return this;
  }

  public InvoiceBuilder name(String name) {
    invoice.setName(name);
    return this;
  }

  public InvoiceBuilder age(int age) {
    invoice.setAge(age);
    return this;
  }

  public InvoiceBuilder issueDate(LocalDate issueDate) {
    invoice.setIssueDate(issueDate);
    return this;
  }

  public InvoiceBuilder entries(List<InvoiceEntry> entries) {
    invoice.setEntries(entries);
    return this;
  }

  public InvoiceBuilder buyer(Company buyer) {
    invoice.setBuyer(buyer);
    return this;
  }

  public InvoiceBuilder seller(Company seller) {
    invoice.setSeller(seller);
    return this;
  }

  public Invoice build() {
    return invoice;
  }
}