package pl.coderstrust.accounting.model;

import pl.coderstrust.accounting.VatRate;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "invoice_entry")
public class InvoiceEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  private String description;

  @NotNull
  private BigDecimal price;

  @NotNull
  @Enumerated(EnumType.STRING)
  private VatRate vatRate;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setVatRate(VatRate vatRate) {
    this.vatRate = vatRate;
  }

  public VatRate getVatRate() {
    return vatRate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvoiceEntry that = (InvoiceEntry) o;
    return id == that.id &&
        Objects.equals(description, that.description) &&
        Objects.equals(price, that.price) &&
        vatRate == that.vatRate;
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, description, price, vatRate);
  }

  public InvoiceEntry(String description, BigDecimal price) {
    this.description = description;
    this.price = price;
  }

  public InvoiceEntry() {
  }
}
