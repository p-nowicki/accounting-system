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
public class InvoiceServiceTest {

  @Mock
  private Database database;

  @Mock
  private Invoice invoice;

  @InjectMocks
  private InvoiceService invoiceService;

  @Test
  public void shouldSaveInvoice() throws IOException {
    //when
    invoiceService.saveInvoice(invoice);

    //then
    verify(database).saveInvoice(invoice);
  }

  @Test
  public void shouldUpdateInvoice() throws IOException, InvoiceNotFoundException {
    //when
    invoiceService.updateInvoice(1, invoice);

    //then
    verify(database).updateInvoice(1, invoice);
  }

  @Test
  public void shouldGetAllInvoices() throws IOException {
    // given
    List<Invoice> invoices = Arrays.asList(new Invoice(), new Invoice());
    when(database.getInvoices()).thenReturn(invoices);

    //when
    Collection<Invoice> result = invoiceService.getInvoices();

    //then
    verify(database).getInvoices();
    assertEquals(invoices, result);
  }

  @Test
  public void shouldRemoveInvoiceById() throws IOException, InvoiceNotFoundException {
    //given
    int testId = 1;

    //when
    invoiceService.removeInvoiceById(testId);

    //then
    verify(database).removeInvoiceById(testId);
  }
}