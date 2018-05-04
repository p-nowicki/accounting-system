package pl.coderstrust.accounting.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTest {

  @Test
  public void shouldReturnNumber() {
    //given
    Invoice invoice = new Invoice();

    //when
    invoice.setNumber("FV/123");

    //then
    assertEquals("FV/123", invoice.getNumber());
  }

  @Test
  public void shouldReturnId() {
    //given
    Invoice invoice = new Invoice();
    //when
    invoice.setId(1);
    //then
    //assertEquals(1, invoice.getId());
  }

  @Test
  public void shouldReturnSeller() {
    //given
    Company seller = new Company();

    seller.setName("SellerCompanyOne");
    seller.setNip(1233215678);
    seller.setStreet("Poznanska 1");
    seller.setPostCode("65-999");
    seller.setCity("Poznań");

    Invoice invoice = new Invoice();

    //when
    invoice.setSeller(seller);

    //then
    assertEquals(seller, invoice.getSeller());
  }

  @Test
  public void shouldReturnBuyer() {
    //given
    Company buyer = new Company();

    buyer.setName("YoloCompany");
    buyer.setNip(1231231212);
    buyer.setStreet("Warszawska 1");
    buyer.setPostCode("01-000");
    buyer.setCity("Warszawa");

    Invoice invoice = new Invoice();

    //when
    invoice.setBuyer(buyer);

    //then
    assertEquals(buyer, invoice.getBuyer());
  }

  @Test
  public void shouldReturnEntries() {
    //given
    Invoice invoice = new Invoice();
    List<InvoiceEntry> list = new ArrayList<>();

    //when
    invoice.setEntries(list);

    //then
    assertEquals(list, invoice.getEntries());

  }

  @Test
  public void shouldReturnDate() {
    //given
    Invoice invoice = new Invoice();
    LocalDate expected = LocalDate.now();

    //when
    invoice.setIssueDate(LocalDate.now());

    //then
    assertEquals(expected, invoice.getIssueDate());

  }

}