package pl.coderstrust.accounting.model;

import java.math.BigDecimal;

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
}
