package pl.coderstrust.accounting.database.memory;

import static junit.framework.TestCase.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.coderstrust.accounting.logic.FileHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class InFileDatabaseTest {

  private String fileName = "src/test/resources/files/newFile2";
  private FileHelper fileHelper = new FileHelper();
  private InFileDatabase inFileDatabase = new InFileDatabase(fileName);
  private Invoice invoice = new Invoice();

  public InFileDatabaseTest() throws IOException {
  }

  @Before
  public void shouldRemoveLinesFromFileBeforeTest() {
    new File(fileName).delete();
  }

  @Test
  public void shouldSaveInvoice() throws IOException {
    //given
    Invoice invoice23 = new Invoice();
    invoice.setName("Lukas");
    invoice.setAge(2);
    String expected = "{\"name\":\"Lukas\",\"age\":2,\"id\":1}";
    //when
    inFileDatabase.saveInvoice(invoice23);
    List<String> given = fileHelper.readLinesFromFile(fileName);
    //then
    assertEquals(expected, given.get(0));
  }

  @Test
  public void shouldRemoveInvoiceInGivenId() throws IOException {
    //given
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
    Invoice testInvoice = new Invoice();
    testInvoice.setName("Piotr");
    testInvoice.setAge(25);
    inFileDatabase.saveInvoice(invoice);
    List<String> expected = Collections.singletonList("{\"name\":\"Piotr\",\"age\":25,\"id\":0}");
    //when
    inFileDatabase.updateInvoice(0, testInvoice);
    List<String> list = fileHelper.readLinesFromFile(fileName);
    //then
    assertEquals(expected, list);
  }
}


