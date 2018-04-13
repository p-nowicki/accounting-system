package pl.coderstrust.accounting;

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


  public Integer getId() {

    return id;

  }

  public BigDecimal getNetValue() {

    for (InvoiceEntry entry : entries){

    }
    return null;
  }

}
