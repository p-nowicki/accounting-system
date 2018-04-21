package database;

import static junit.framework.TestCase.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class InFileDatabaseTest {

  private String fileName = "src/test/resources/files/newFile";
  private FileHelper fileHelper = new FileHelper();
  private InFileDatabase inFileDatabase = new InFileDatabase(fileName);

  @Before
  public void clearEveryFileBeforeTest() {
    new File(fileName).delete();
  }

  @Test
  public void shouldSaveInvoice() throws IOException {
    //given
    Invoice invoice23 = new Invoice();
    invoice23.setName("Lukas");
    invoice23.setAge(2);
    String expected = "{\"name\":\"Lukas\",\"age\":2,\"id\":1}";
    //when
    inFileDatabase.saveInvoice(invoice23);
    List<String> given = fileHelper.readLinesFromFile(fileName);
    //then
    assertEquals(expected, given.get(0));
  }

  @Test
  public void shouldGiveInvoice() throws IOException {
    //given
    Invoice invoice23 = new Invoice();
    invoice23.setName("Lukas");
    invoice23.setAge(2);
    List<String> expected = Collections.singletonList("{\"name\":\"Lukas\",\"age\":2,\"id\":1}");
    //when
    inFileDatabase.saveInvoice(invoice23);
    List<String> list = fileHelper.readLinesFromFile(fileName);
    //then
    assertEquals(expected, list);
  }

  @Test
  public void shouldRemoveInvoiceInGivenId() throws IOException {
    Invoice invoice = new Invoice();
    final FileHelper fileHelper = new FileHelper();
    //given
    new File(fileName).delete();
    inFileDatabase.saveInvoice(invoice);
    inFileDatabase.saveInvoice(invoice);
    inFileDatabase.saveInvoice(invoice);
    inFileDatabase.saveInvoice(invoice);
    inFileDatabase.saveInvoice(invoice);
    //when
    inFileDatabase.removeInvoiceById(2);
    List<String> list = fileHelper.readLinesFromFile(fileName);
    //then
    Assert.assertEquals(4, list.size());
  }

  @Test
  public void shouldUpdateExistingInvoice() throws IOException {
    //given
    Invoice invoice = new Invoice();
    Invoice testInvoice = new Invoice("Piotr", 25);
    final FileHelper fileHelper = new FileHelper();
    new File(fileName).delete();
    inFileDatabase.saveInvoice(invoice);
    List<String> expected = Collections.singletonList("{\"name\":\"Piotr\",\"age\":25,\"id\":1}");
    //when
    inFileDatabase.updateInvoice(1, testInvoice);
    List<String> list = fileHelper.readLinesFromFile(fileName);
    //then
    assertEquals(expected, list);
  }
}


