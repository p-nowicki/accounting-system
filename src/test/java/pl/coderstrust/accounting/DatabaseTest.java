package pl.coderstrust.accounting;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

public abstract class DatabaseTest {

  protected abstract Database getDatabase();


  @Test
  public void shouldReturnToInvoicesWhenToInvoicesWhereAdded() {
    Database db = getDatabase();
    db.saveInvoice(new Invoice());


  }

}