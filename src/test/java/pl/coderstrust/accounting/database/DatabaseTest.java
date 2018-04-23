package pl.coderstrust.accounting.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import pl.coderstrust.accounting.database.impl.file.InvoiceProvider;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;

public abstract class DatabaseTest {

  protected abstract Database getDatabase() throws IOException;

  @Test
  public void shouldSaveInvoice() throws IOException {
    //given
    Invoice invoiceProviderOne = new InvoiceProvider().InvoiceOne();
    Invoice invoiceProviderTwo = new InvoiceProvider().InvoiceTwo();
    Invoice invoiceProviderThree = new InvoiceProvider().InvoiceThree();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    db.saveInvoice(invoiceProviderThree);

    //then
    assertNotNull(db.getInvoices());
    assertEquals(3, db.getInvoices().size());

    Invoice result = db.getInvoices().iterator().next();
    assertEquals(invoiceProviderOne, result);
  }

  @Test
  public void shouldSave3InvoicesAndVerifyID() throws IOException {
    //given
    Invoice invoiceProviderOne = new InvoiceProvider().InvoiceOne();
    Invoice invoiceProviderTwo = new InvoiceProvider().InvoiceTwo();
    Invoice invoiceProviderThree = new InvoiceProvider().InvoiceThree();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    db.saveInvoice(invoiceProviderThree);

    //then
    int idOne = invoiceProviderOne.getId(); // I am not sure it should be in then.
    int idTwo = invoiceProviderTwo.getId();
    int idThree = invoiceProviderThree.getId();
    assertEquals(0, idOne);
    assertEquals(1, idTwo);
    assertEquals(2, idThree);
  }

  @Test
  public void shouldSave3InvoicesAndRemoveOne() throws IOException, InvoiceNotFoundException {
    //given
    Invoice invoiceProviderOne = new InvoiceProvider().InvoiceOne();
    Invoice invoiceProviderTwo = new InvoiceProvider().InvoiceTwo();
    Invoice invoiceProviderThree = new InvoiceProvider().InvoiceThree();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    db.saveInvoice(invoiceProviderThree);
    db.removeInvoiceById(0);

    //then
    assertEquals(2, db.getInvoices().size());
  }

  @Test
  public void abc() throws IOException, InvoiceNotFoundException {
    //given
    Invoice invoiceProviderOne = new InvoiceProvider().InvoiceOne();
    Invoice invoiceProviderTwo = new InvoiceProvider().InvoiceTwo();
    Database db = getDatabase();

    //when
    db.saveInvoice(invoiceProviderOne);
    db.saveInvoice(invoiceProviderTwo);
    invoiceProviderOne.setNumber("ABC12");
    db.updateInvoice(invoiceProviderOne);

    //then
    assertEquals("ABC12", invoiceProviderOne.getNumber());
  }
  // prepare 3 invoices for tests with all data filled, in class InvoiceProvider DONE
  // add 3 invoices and verify id is unique and generated DONE
  // add 3 invoices, remove 1, verify if 2 exists DONE
  // add 2 invoices, modify 1, get it by id, verify if modified, verify id was not changed
}