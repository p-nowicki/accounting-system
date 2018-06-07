package pl.coderstrust.accounting.helpers;

import pl.coderstrust.accounting.VatRate;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestInvoiceProvider {

  public Invoice invoiceOne() {
    Company buyer = new Company();
    buyer.setName("YoloCompany");
    buyer.setNip(199);
    buyer.setStreet("Warszawska 1");
    buyer.setPostCode("01-000");
    buyer.setCity("Warszawa");

    Company seller = new Company();
    seller.setName("SellerCompanyOne");
    seller.setNip(1233215678);
    seller.setStreet("Poznanska 1");
    seller.setPostCode("65-999");
    seller.setCity("Poznan");

    Invoice invoice = new Invoice();
    invoice.setNumber("FV123/1");
    invoice.setIssueDate(LocalDate.of(2017, 12, 30));
    invoice.setBuyer(buyer);
    invoice.setSeller(seller);

    List<InvoiceEntry> list = new ArrayList();
    invoice.setEntries(list);
    BigDecimal price = (BigDecimal.valueOf(1000));
    String description = "abc";
    InvoiceEntry invoiceEntry = new InvoiceEntry(description, price);
    invoiceEntry.setVatRate(VatRate.FIVE);
    list.add(invoiceEntry);

    return invoice;
  }

  public Invoice invoiceTwo() {
    Company buyer = new Company();
    buyer.setName("ABCCompany");
    buyer.setNip(1212366991);
    buyer.setStreet("Pawla 1");
    buyer.setPostCode("01-000");
    buyer.setCity("Warszawa");

    Company seller = new Company();
    seller.setName("AdamCompanyOne");
    seller.setNip(199);
    seller.setStreet("Krakowska 1");
    seller.setPostCode("65-999");
    seller.setCity("Poznan");

    Invoice invoice = new Invoice();
    invoice.setNumber("FV123/2");
    invoice.setIssueDate(LocalDate.of(2017, 12, 12));
    invoice.setBuyer(buyer);
    invoice.setSeller(seller);

    List<InvoiceEntry> list = new ArrayList();
    invoice.setEntries(list);
    BigDecimal price = (BigDecimal.valueOf(350));
    String description = "abc";
    InvoiceEntry invoiceEntry = new InvoiceEntry(description, price);
    invoiceEntry.setVatRate(VatRate.TWENTY_THREE);
    list.add(invoiceEntry);

    return invoice;
  }

  public Invoice invoiceThree() {
    Company buyer = new Company();
    buyer.setName("ABCCompany");
    buyer.setNip(199);
    buyer.setStreet("Pawla 1");
    buyer.setPostCode("01-000");
    buyer.setCity("Warszawa");

    Company seller = new Company();
    seller.setName("KamilCompanyOne");
    seller.setNip(1778886789);
    seller.setStreet("Gdanska 1");
    seller.setPostCode("65-999");
    seller.setCity("Poznan");

    Invoice invoice = new Invoice();
    invoice.setNumber("FV123/3");
    invoice.setIssueDate(LocalDate.of(2017, 11, 30));
    invoice.setBuyer(buyer);
    invoice.setSeller(seller);

    List<InvoiceEntry> list = new ArrayList();
    invoice.setEntries(list);
    BigDecimal price = (BigDecimal.valueOf(2500));
    String description = "abc2";
    InvoiceEntry invoiceEntry = new InvoiceEntry(description, price);
    invoiceEntry.setVatRate(VatRate.EIGHT);
    list.add(invoiceEntry);

    return invoice;
  }

  public Invoice invoiceFour() {
    Company buyer = new Company();
    buyer.setName("KamilCompanyOne");
    buyer.setNip(1778886789);
    buyer.setStreet("Pawla 1");
    buyer.setPostCode("01-000");
    buyer.setCity("Warszawa");

    Company seller = new Company();
    seller.setName("KamilCompanyOne");
    seller.setNip(199);
    seller.setStreet("Gdanska 1");
    seller.setPostCode("65-999");
    seller.setCity("Poznan");

    Invoice invoice = new Invoice();
    invoice.setNumber("FV123/3");
    invoice.setIssueDate(LocalDate.of(2017, 11, 30));
    invoice.setBuyer(buyer);
    invoice.setSeller(seller);

    List<InvoiceEntry> list = new ArrayList();
    invoice.setEntries(list);
    BigDecimal price = (BigDecimal.valueOf(1500));
    String description = "abc2";
    InvoiceEntry invoiceEntry = new InvoiceEntry(description, price);
    invoiceEntry.setVatRate(VatRate.TWENTY_THREE);
    list.add(invoiceEntry);

    return invoice;
  }

  public Invoice invoiceFive() {
    Company buyer = new Company();
    buyer.setName("KamilCompanyOne");
    buyer.setNip(199);
    buyer.setStreet("Pawla 1");
    buyer.setPostCode("01-000");
    buyer.setCity("Warszawa");

    Company seller = new Company();
    seller.setName("KamilCompanyOne");
    seller.setNip(199222333);
    seller.setStreet("Gdanska 1");
    seller.setPostCode("65-999");
    seller.setCity("Poznan");

    Invoice invoice = new Invoice();
    invoice.setNumber("FV123/3");
    invoice.setIssueDate(LocalDate.of(2017, 11, 30));
    invoice.setBuyer(buyer);
    invoice.setSeller(seller);

    List<InvoiceEntry> list = new ArrayList();
    invoice.setEntries(list);
    BigDecimal price = (BigDecimal.valueOf(11500));
    String description = "abc2";
    InvoiceEntry invoiceEntry = new InvoiceEntry(description, price);
    invoiceEntry.setVatRate(VatRate.TWENTY_THREE);
    list.add(invoiceEntry);

    return invoice;
  }
}
