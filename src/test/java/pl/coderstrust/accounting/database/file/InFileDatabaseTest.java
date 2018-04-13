package pl.coderstrust.accounting.database.file;

import static org.junit.Assert.*;

import org.junit.Test;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.DatabaseTest;

public class InFileDatabaseTest extends DatabaseTest{

  @Override
  protected Database getDatabase() {
    return new InFileDatabase();
  }

  @Test
  public void shouldCreateFileWhenInvoiceAdded(){
    new InFileDatabase();
  }

}