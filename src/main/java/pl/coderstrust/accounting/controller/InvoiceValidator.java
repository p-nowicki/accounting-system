package pl.coderstrust.accounting.controller;

import pl.coderstrust.accounting.model.Invoice;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceValidator {

  public InvoiceValidator() {
  }

  public List<String> validate(Invoice invoice) {
    List<String> list = new ArrayList<>();
    if (invoice.getBuyer() == null) {
      list.add(" Buyer is required ");
    }
    if (invoice.getSeller() == null) {
      list.add(" Seller is required ");
    }
    if (LocalDateTime.now().isBefore(ChronoLocalDateTime.from(invoice.getIssueDate()))) {
      list.add(" Correct date required ");
      return list;
    }
    if (invoice.getNumber() == null) {
      list.add("Number is required");
    }
    if (invoice.getId() < 0) {
      list.add(" Not correct id ");
    }
    if (invoice.getAge() < 18) {
      list.add(" Person under 18 years old ");
    }
    return list;
  }
}