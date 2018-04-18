package pl.coderstrust.accounting.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import pl.coderstrust.accounting.model.Invoice;

public abstract class DatabaseTest {

  protected abstract Database getDatabase();

  @Test
  public void shouldSaveInvoice() {
    //given
    Invoice given = new Invoice();
    Database db = getDatabase();

    //when
    db.saveInvoice(given);

    //then
    assertNotNull(db.getInvoices());
    assertEquals(1, db.getInvoices().size());

    Invoice result = db.getInvoices().iterator().next();
    assertEquals(given, result);
  }

  // prepare 3 invoices for tests with all data filled, in class InvoiceProvider
  // add 3 invoices and verify id is unique and generated
  // add 3 invoices, remove 1, verify if 2 exists
  // add 2 invoices, modify 1, get it by id, verify if modified, verify id was not changed
}