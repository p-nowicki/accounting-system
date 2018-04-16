package pl.coderstrust.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Invoice {

  private int id;
  private String number;

  private LocalDate issueDate;

  private Company buyer;
  private Company seller;

  private List<InvoiceEntry> entries;

  public void setId(int id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public Company getBuyer() {
    return buyer;
  }

  public void setBuyer(Company buyer) {
    this.buyer = buyer;
  }

  public Company getSeller() {
    return seller;
  }

  public void setSeller(Company seller) {
    this.seller = seller;
  }

  public List<InvoiceEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<InvoiceEntry> entries) {
    this.entries = entries;
  }

  public Integer getId() {
    return id;
  }

  public BigDecimal getNetValue() {

    BigDecimal sum = BigDecimal.ZERO;

    for (InvoiceEntry entry : entries) {
      sum = sum.add(entry.getPrice());
    }
    return sum;
  }
}
