package pl.coderstrust.accounting;

import org.junit.Test;
import pl.coderstrust.accounting.logic.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;

public abstract class DatabaseTest {

  protected abstract Database getDatabase();

  @Test
  private void shouldReturnToInvoicesWhenToInvoicesWhereAdded() throws IOException {
    Database db = getDatabase();
    db.saveInvoice(new Invoice());
  }
}