package pl.coderstrust.accounting.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {

  private String description;

  private BigDecimal price;

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
}
