package pl.coderstrust.accounting.model;

import pl.coderstrust.accounting.VatRate;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity(name = "invoice_entry")
public class InvoiceEntry {

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

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    InvoiceEntry that = (InvoiceEntry) other;
    return Objects.equals(description, that.description)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, price);
  }

  public InvoiceEntry(String description, BigDecimal price) {
    this.description = description;
    this.price = price;
  }

  public InvoiceEntry() {
  }
}
