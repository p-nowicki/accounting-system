package pl.coderstrust.accounting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

@Entity(name = "invoice")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  private String number;

  @NotNull
  private String name;

  @NotNull
  private int age;

  @NotNull
  private LocalDate issueDate;

  @ManyToOne
  private Company buyer;

  @ManyToOne
  private Company seller;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "invoice", referencedColumnName = "id")
  @OrderColumn
  private List<InvoiceEntry> entries = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

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

  @JsonIgnore
  public BigDecimal getNetValue() {
    BigDecimal sum = BigDecimal.ZERO;

    for (InvoiceEntry entry : entries) {
      sum = sum.add(entry.getPrice());
    }
    return sum;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Invoice invoice = (Invoice) other;
    return id == invoice.id
        && Objects.equals(number, invoice.number)
        && Objects.equals(issueDate, invoice.issueDate)
        && Objects.equals(buyer, invoice.buyer)
        && Objects.equals(seller, invoice.seller)
        && Objects.equals(entries, invoice.entries);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, number, issueDate, buyer, seller, entries);
  }

  @Override
  public String toString() {
    return "Invoice{"
        + "id=" + id
        + ", number='" + number + '\''
        + ", issueDate=" + issueDate
        + ", buyer=" + buyer
        + ", seller=" + seller
        + ", entries=" + entries
        + '}';
  }
}
