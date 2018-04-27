package pl.coderstrust.accounting.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompanyTest {

  @Test
  public void shouldReturnName() {
    //given
    Company company = new Company();

    //when
    company.setName("Jan Kowalski");

    //then
    assertEquals("Jan Kowalski", company.getName());
  }

  @Test
  public void shouldReturnNip() {
    //given
    Company company = new Company();

    //when
    company.setNip(1231231212);

    //then
    assertEquals(1231231212, company.getNip());
  }

  @Test
  public void shouldReturnStreet() {
    //given
    Company company = new Company();

    //when
    company.setStreet("Kocia 123");

    //then
    assertEquals("Kocia 123", company.getStreet());
  }

  @Test
  public void shouldReturnPostCode() {
    //given
    Company company = new Company();

    //when
    company.setPostCode("01-456");

    //then
    assertEquals("01-456", company.getPostCode());
  }

  @Test
  public void shouldReturnCity() {
    //given
    Company company = new Company();

    //when
    company.setCity("Warszawa");

    //then
    assertEquals("Warszawa", company.getCity());
  }
}