package pl.coderstrust.accounting.database.impl.file;

import static junit.framework.TestCase.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.coderstrust.accounting.database.impl.helpers.FileHelper;
import pl.coderstrust.accounting.database.impl.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.database.impl.helpers.IdGenerator;
import pl.coderstrust.accounting.database.impl.helpers.IdGeneratorForInFileDataBase;
import pl.coderstrust.accounting.database.impl.helpers.ObjectMapperHelper;
import pl.coderstrust.accounting.database.impl.multifile.PathHelper;
import pl.coderstrust.accounting.helpers.TestInvoiceProvider;
import pl.coderstrust.accounting.model.Invoice;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class InFileDatabaseTest {

  private File file = new File("src/test/resources/files/newFile");
  private FileHelper fileHelper = new FileHelper();
  private ObjectMapperHelper objectMapperHelper = new ObjectMapperHelper();
  private FileInvoiceHelper fileInvoiceHelper = new FileInvoiceHelper(fileHelper,
      objectMapperHelper);
  private IdGeneratorForInFileDataBase idGenerator = new IdGeneratorForInFileDataBase(file, fileInvoiceHelper);
  private InFileDatabase inFileDatabase = new InFileDatabase(file, fileInvoiceHelper, idGenerator);

  @Before
  public void shouldRemoveFileBeforeTest() {
    file.delete();
    inFileDatabase = new InFileDatabase(file, fileInvoiceHelper, idGenerator);
  }

  @Test
  public void shouldSaveInvoice() throws IOException {
    //given
    String expected = new ObjectMapperHelper().convertInvoiceToJson(TestInvoiceProvider.invoiceOne());
    //when
    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceOne());
    //then
    List<String> given = fileHelper.readLinesFromFile(file);
    assertEquals(expected, given.get(0));
  }

  @Test
  public void shouldRemoveInvoiceInGivenId() throws IOException {
    //given
    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceOne());
    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceTwo());
    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceThree());
    //when
    inFileDatabase.removeInvoiceById(2);
    //then
    List<Invoice> list = inFileDatabase.getInvoices();
    for (Invoice invoice : list) {
      Assert.assertFalse(invoice.getId() == 2);
    }
    Assert.assertEquals(2, list.size());
  }

  @Test
  public void shouldUpdateExistingInvoice() throws IOException {
    //given
    Invoice testInvoice = new Invoice();
    testInvoice.setName("Piotr");
    testInvoice.setAge(25);

    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceOne());

    List<String> expected = Arrays
        .asList(new ObjectMapperHelper().convertInvoiceToJson(testInvoice));

    //when
    inFileDatabase.updateInvoice(1, testInvoice);

    //then
    List<String> list = fileHelper.readLinesFromFile(file);
    assertEquals(expected, list);
  }

  @Test
  public void checkIsGoodInvoiceSearch() throws IOException {
    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceOne());
    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceTwo());
    inFileDatabase.saveInvoice(TestInvoiceProvider.invoiceThree());
    String expected = "Invoice{id=1, number='FV123/1', issueDate=2017-12-30, "
        + "buyer=Company{name='YoloCompany', nip=1231231212, street='Warszawska 1', "
        + "postCode='01-000', city='Warszawa'}, seller=Company{name='SellerCompanyOne',"
        + " nip=1233215678, street='Poznanska 1', postCode='65-999', city='Poznan'}, entries=[]}";
    String given = String.valueOf(inFileDatabase.getInvoiceById(1));

    assertEquals(expected, given);
  }
}