package pl.coderstrust.accounting.logic;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceBookTest {

  @Mock
  private Database database;

  @Mock
  private Invoice invoice;

  @InjectMocks
  private InvoiceBook invoiceBook;

  @Test
  public void shouldSaveInvoice() {
    //when
    invoiceBook.saveInvoice(invoice);

    //then
    verify(database).saveInvoice(invoice);
  }
  @Test
  public void shouldUpdateInvoice() {
    //when
    invoiceBook.updateInvoice(invoice);

    //then
    verify(database).updateInvoice(invoice);
  }
  @Test
  public void shouldGetInvoice() {
    //when
    invoiceBook.getInvoices();

    //then
    verify(database).getInvoices();
  }
  @Test
  public void shouldRemoveInvoiceById() {
    //given
    int testId = 1;

    //when
    invoiceBook.removeInvoiceById(testId);

    //then
    verify(database).removeInvoiceById(testId);
  }
}