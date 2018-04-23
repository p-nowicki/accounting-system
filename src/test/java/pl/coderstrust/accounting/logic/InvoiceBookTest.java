package pl.coderstrust.accounting.logic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.exceptions.InvoiceNotFoundException;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceBookTest {

  @Mock
  private Database database;

  @Mock
  private Invoice invoice;

  @InjectMocks
  private InvoiceBook invoiceBook;

  @Test
  public void shouldSaveInvoice() throws IOException {
    //when
    invoiceBook.saveInvoice(invoice);

    //then
    verify(database).saveInvoice(invoice);
  }

  @Test
  public void shouldUpdateInvoice() throws InvoiceNotFoundException, IOException {
    //when
    invoiceBook.updateInvoice(invoice);

    //then
    verify(database).updateInvoice(invoice);
  }

  @Test
  public void shouldGetAllInvoices() throws IOException {
    // given
    List<Invoice> invoices = Arrays.asList(new Invoice(), new Invoice());
    when(database.getInvoices()).thenReturn(invoices);

    //when
    Collection<Invoice> result = invoiceBook.getInvoices();

    //then
    verify(database).getInvoices();
    assertEquals(invoices, result);
  }

  @Test
  public void shouldRemoveInvoiceById() throws InvoiceNotFoundException, IOException {
    //given
    int testId = 1;

    //when
    invoiceBook.removeInvoiceById(testId);

    //then
    verify(database).removeInvoiceById(testId);
  }
}