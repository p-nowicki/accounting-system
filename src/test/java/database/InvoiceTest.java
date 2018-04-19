package database;

import org.junit.Assert;
import org.junit.Test;

public class InvoiceTest {

  @Test
  public void setAndReadFileDataInNewFile() {
    //given
    Invoice invoice2 = new Invoice();
    //when
    invoice2.setAge(13);
    invoice2.setName("Bob");
    invoice2.setId(0);
    //then
    Assert.assertEquals("Bob", invoice2.getName());
    Assert.assertEquals(13, invoice2.getAge());
    Assert.assertEquals(0, invoice2.getId());
  }
}