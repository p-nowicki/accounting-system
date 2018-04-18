package pl.coderstrust.accounting.database.memory;

import org.junit.Test;
import pl.coderstrust.accounting.InMemoryDatabase;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.DatabaseTest;
import pl.coderstrust.accounting.model.Invoice;

public class InMemoryDatabaseTest extends DatabaseTest {

  @Override
  protected Database getDatabase() {
    return new InMemoryDatabase();
  }

  @Test
  public void shouldSaveInvoice() {
    //given
    Invoice given = new Invoice();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase();
    //when
    inMemoryDatabase.saveInvoice(given);
    //then
    inMemoryDatabase.getInvoices();
  }
}