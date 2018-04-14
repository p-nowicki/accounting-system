package pl.coderstrust.accounting.database;

import static org.junit.Assert.*;

import org.junit.Test;
import pl.coderstrust.accounting.model.Invoice;

public abstract class DatabaseTest {

  protected abstract Database getDatabase();

  @Test
  public void shouldReturn2InvoicesWhen2InvoicesAreAdded (){

    Database db = getDatabase();

    db.saveInvoice(new Invoice());

  }

}