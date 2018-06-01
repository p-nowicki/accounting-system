package pl.coderstrust.accounting;

import java.math.BigDecimal;

public enum VatRate {

    TAXFREE(BigDecimal.valueOf(0)),
    ZERO(BigDecimal.valueOf(0)),
    FIVE(BigDecimal.valueOf(0.05)),
    EIGHT(BigDecimal.valueOf(0.08)),
    TWENTYTHREE(BigDecimal.valueOf(0.23));

    private BigDecimal vatPercent;

    private VatRate(BigDecimal vatPercent) {
      this.vatPercent = vatPercent;
    }

    public BigDecimal getVatPercent() {
      return vatPercent;
    }
    //private vatVariants(BigDecimal percent, String name) {
    //vatPercent = percent;
    //vatName = name;
    //}

    //private int vatCallculator() {
    //return
  }

