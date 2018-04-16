package pl.coderstrust.accounting.logic;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import pl.coderstrust.accounting.database.Database;

public class InvoiceBookTest {


  @Test
  public void shouldSaveInvoice() {

    Database database = mock(Database.class);

    new InvoiceBook(database);

  }

}