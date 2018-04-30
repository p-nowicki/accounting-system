package pl.coderstrust.accounting.database.impl.file;

import static junit.framework.TestCase.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.coderstrust.accounting.database.impl.helpers.FileHelper;
import pl.coderstrust.accounting.database.impl.helpers.ObjectMapperHelper;
import pl.coderstrust.accounting.helpers.TestInvoiceProvider;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class InFileDatabaseTest {

  private File file = new File("src/test/resources/newFile2.txt");
  private FileHelper fileHelper = new FileHelper();
  private InFileDatabase inFileDatabase;
  private TestInvoiceProvider invoiceProvider = new TestInvoiceProvider();
  // TODO invoice provider should have static methods - no need for object

  @Before
  public void shouldRemoveFileBeforeTest() {
    file.delete();
    inFileDatabase = new InFileDatabase(file);
  }

  @Test
  public void shouldSaveInvoice() throws IOException {
    //given
    Invoice invoice = invoiceProvider.invoiceOne();
    invoice.setId(1);

    String expected = new ObjectMapperHelper().convertInvoiceToJson(invoice);

    //when
    inFileDatabase.saveInvoice(invoice);

    //then
    List<String> given = fileHelper.readLinesFromFile(file);
    assertEquals(expected, given.get(0));
  }

  @Test
  public void shouldRemoveInvoiceInGivenId() throws IOException {
    //given
    inFileDatabase.saveInvoice(invoiceProvider.invoiceOne());
    inFileDatabase.saveInvoice(invoiceProvider.invoiceTwo());
    inFileDatabase.saveInvoice(invoiceProvider.invoiceThree());

    //when
    inFileDatabase.removeInvoiceById(2);

    //then
    List<String> list = fileHelper.readLinesFromFile(file);
    Assert.assertEquals(2, list.size());

    // TODO -- verify if good invoice was removed
  }

  @Test
  public void shouldUpdateExistingInvoice() throws IOException {
    //given
    Invoice testInvoice = new Invoice();
    testInvoice.setId(1);
    testInvoice.setName("Piotr");
    testInvoice.setAge(25);

    inFileDatabase.saveInvoice(invoiceProvider.invoiceOne());

    List<String> expected = Arrays
        .asList(new ObjectMapperHelper().convertInvoiceToJson(testInvoice));

    //when
    inFileDatabase.updateInvoice(1, testInvoice);

    //then
    List<String> list = fileHelper.readLinesFromFile(file);
    assertEquals(expected, list);
  }
}


