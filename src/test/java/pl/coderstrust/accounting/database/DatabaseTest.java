package pl.coderstrust.accounting.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.helpers.TestInvoiceProvider;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.sql.SQLException;

public abstract class DatabaseTest {

  protected abstract Database getDatabase() throws IOException, SQLException;

  @Test
  public void shouldSaveInvoice() throws IOException, SQLException {
    //given
    Invoice invoiceProviderOne = new TestInvoiceProvider().invoiceOne();
    Invoice invoiceProviderTwo = new TestInvoiceProvider().invoiceTwo();
    Invoice invoiceProviderThree = new TestInvoiceProvider().invoiceThree();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    db.saveInvoice(invoiceProviderThree);

    //then
    assertNotNull(db.getInvoices());
    assertEquals(3, db.getInvoices().size());

    Invoice result = db.getInvoiceById(1);
    assertEquals(invoiceProviderOne, result);
  }

  @Test
  public void shouldSave3InvoicesAndVerifyId() throws IOException, SQLException {
    //given
    Invoice invoiceProviderOne = new TestInvoiceProvider().invoiceOne();
    Invoice invoiceProviderTwo = new TestInvoiceProvider().invoiceTwo();
    Invoice invoiceProviderThree = new TestInvoiceProvider().invoiceThree();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    db.saveInvoice(invoiceProviderThree);

    //then
    int idOne = invoiceProviderOne.getId(); // I am not sure it should be in then.
    int idTwo = invoiceProviderTwo.getId();
    int idThree = invoiceProviderThree.getId();
    assertEquals(1, idOne);
    assertEquals(2, idTwo);
    assertEquals(3, idThree);
  }

  @Test
  public void shouldSave3InvoicesAndRemoveOne() throws IOException, InvoiceNotFoundException, SQLException {
    //given
    Invoice invoiceProviderOne = new TestInvoiceProvider().invoiceOne();
    Invoice invoiceProviderTwo = new TestInvoiceProvider().invoiceTwo();
    Invoice invoiceProviderThree = new TestInvoiceProvider().invoiceThree();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    db.saveInvoice(invoiceProviderThree);
    db.removeInvoiceById(1);
    db.getInvoices();

    //then
    assertEquals(2, db.getInvoices().size());
  }

  @Test
  public void shouldSave2InvoicesAndUpdateOneInvoiceNumber()
      throws IOException, InvoiceNotFoundException, SQLException {
    //given
    Invoice invoiceProviderOne = new TestInvoiceProvider().invoiceOne();
    Invoice invoiceProviderTwo = new TestInvoiceProvider().invoiceTwo();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    invoiceProviderOne.setNumber("ABC12");
    db.updateInvoice(1, invoiceProviderOne);

    //then
    assertEquals("ABC12", invoiceProviderOne.getNumber());
  }
  // prepare 3 invoices for tests with all data filled, in class InvoiceProvider DONE
  // add 3 invoices and verify id is unique and generated DONE
  // add 3 invoices, remove 1, verify if 2 exists DONE
  // add 2 invoices, modify 1, get it by id, verify if modified, verify id was not changed
}