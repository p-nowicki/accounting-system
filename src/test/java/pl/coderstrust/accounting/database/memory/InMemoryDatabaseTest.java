package pl.coderstrust.accounting.database.memory;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.coderstrust.accounting.InMemoryDatabase;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.DatabaseTest;
import pl.coderstrust.accounting.model.Invoice;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabaseTest {

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