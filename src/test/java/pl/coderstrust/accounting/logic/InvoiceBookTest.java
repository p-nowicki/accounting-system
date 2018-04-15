package pl.coderstrust.accounting.logic;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceBookTest {


    @Mock
    Database database;
    Invoice invoice;

    @InjectMocks
    InvoiceBook invoiceBook;

  @Test
  public void shouldSaveInvoice() {


  }


}