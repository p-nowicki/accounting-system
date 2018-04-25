package pl.coderstrust.accounting.model;

import org.junit.Assert;
import org.junit.Test;
import pl.coderstrust.accounting.model.Invoice;

public class InvoiceTest {

  @Test
  public void setAndReadFileDataInNewFile() {
    //given
    Invoice invoice2 = new Invoice();
    //when
    invoice2.setAge(13);
    invoice2.setName("Bob");
    //then
    Assert.assertEquals("Bob", invoice2.getName());
    Assert.assertEquals(13, invoice2.getAge());
  }
}