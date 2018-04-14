package pl.coderstrust.accounting;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public abstract class DatabaseTest {

  protected abstract Database getDatabase();


  @Test
  public void shouldReturnToInvoicesWhenToInvoicesWhereAdded() {
    Database db = getDatabase();
    db.saveInvoice(new Invoice());


  }

}