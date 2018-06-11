package pl.coderstrust.accounting;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.coderstrust.accounting.helpers.TestInvoiceProvider;
import pl.coderstrust.accounting.logic.InvoiceService;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class TaxCalculatorTest {

  @Mock
  InvoiceService invoiceService;

  @Test
  public void ReturnCostsWithoutInvoices() throws IOException {
    //given
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(0);

    //when
    BigDecimal result = taxCalculator.getCosts();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnCostsFromOneInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(1000);
    Invoice invoice = testInvoiceProvider.invoiceOne();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getCosts();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnCostsFromFiveInvoices() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(15000);
    BigDecimal result;

    Invoice invoiceOne = testInvoiceProvider.invoiceOne();
    Invoice invoiceTwo = testInvoiceProvider.invoiceTwo();
    Invoice invoiceThree = testInvoiceProvider.invoiceThree();
    Invoice invoiceFour = testInvoiceProvider.invoiceFour();
    Invoice invoiceFive = testInvoiceProvider.invoiceFive();

    //when
    when(invoiceService.getInvoices()).thenReturn(Arrays.asList(invoiceOne, invoiceTwo, invoiceThree, invoiceFour, invoiceFive));
    result = taxCalculator.getCosts();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnRevenuesWithoutInvoices() throws IOException {
    //given
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(0);

    //when
    BigDecimal result = taxCalculator.getRevenues();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnRevenuesFromOneInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(350);
    Invoice invoice = testInvoiceProvider.invoiceTwo();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getRevenues();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnRevenuesFromFiveInvoices() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(1850);
    BigDecimal result;

    Invoice invoiceOne = testInvoiceProvider.invoiceOne();
    Invoice invoiceTwo = testInvoiceProvider.invoiceTwo();
    Invoice invoiceThree = testInvoiceProvider.invoiceThree();
    Invoice invoiceFour = testInvoiceProvider.invoiceFour();
    Invoice invoiceFive = testInvoiceProvider.invoiceFive();

    //when
    when(invoiceService.getInvoices()).thenReturn(Arrays.asList(invoiceOne, invoiceTwo, invoiceThree, invoiceFour, invoiceFive));
    result = taxCalculator.getRevenues();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatOutcomeWithoutInvoice() throws IOException {
    //given
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(0);

    //when
    BigDecimal result = taxCalculator.getVatOutcome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatOutcomeFromOneBuyInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(0);
    Invoice invoice = testInvoiceProvider.invoiceOne();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getVatOutcome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatOutcomeFromOneSellInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(80.50).setScale(2);
    Invoice invoice = testInvoiceProvider.invoiceTwo();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getVatOutcome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatOutcomeFromFiveInvoices() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(425.50).setScale(2);
    BigDecimal result;

    Invoice invoiceOne = testInvoiceProvider.invoiceOne();
    Invoice invoiceTwo = testInvoiceProvider.invoiceTwo();
    Invoice invoiceThree = testInvoiceProvider.invoiceThree();
    Invoice invoiceFour = testInvoiceProvider.invoiceFour();
    Invoice invoiceFive = testInvoiceProvider.invoiceFive();

    //when
    when(invoiceService.getInvoices()).thenReturn(Arrays.asList(invoiceOne, invoiceTwo, invoiceThree, invoiceFour, invoiceFive));
    result = taxCalculator.getVatOutcome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatIncomeWithoutInvoice() throws IOException {
    //given
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(0);

    //when
    BigDecimal result = taxCalculator.getVatIncome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatIncomeFromOneBuyInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(50.00).setScale(2);
    Invoice invoice = testInvoiceProvider.invoiceOne();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getVatIncome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatIncomeFromOneSellInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(0);
    Invoice invoice = testInvoiceProvider.invoiceTwo();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getVatIncome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatIncomeFromFiveInvoices() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(2895).setScale(2);
    BigDecimal result;

    Invoice invoiceOne = testInvoiceProvider.invoiceOne();
    Invoice invoiceTwo = testInvoiceProvider.invoiceTwo();
    Invoice invoiceThree = testInvoiceProvider.invoiceThree();
    Invoice invoiceFour = testInvoiceProvider.invoiceFour();
    Invoice invoiceFive = testInvoiceProvider.invoiceFive();

    //when
    when(invoiceService.getInvoices()).thenReturn(Arrays.asList(invoiceOne, invoiceTwo, invoiceThree, invoiceFour, invoiceFive));
    result = taxCalculator.getVatIncome();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatDifferenceWithoutInvoice() throws IOException {
    //given
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(0);

    //when
    BigDecimal result = taxCalculator.getVatDifference();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatDifferenceFromOneBuyInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(-50.00).setScale(2);
    Invoice invoice = testInvoiceProvider.invoiceOne();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getVatDifference();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatDifferenceFromOneSellInvoice() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(80.50).setScale(2);
    Invoice invoice = testInvoiceProvider.invoiceTwo();
    when(invoiceService.getInvoices()).thenReturn(Collections.singleton(invoice));

    //when
    BigDecimal result = taxCalculator.getVatDifference();

    //then
    assertEquals(expected, result);

  }

  @Test
  public void ReturnVatDifferenceFromFiveInvoices() throws IOException {
    //given
    TestInvoiceProvider testInvoiceProvider = new TestInvoiceProvider();
    TaxCalculator taxCalculator = new TaxCalculator(invoiceService);
    BigDecimal expected = BigDecimal.valueOf(-2469.50).setScale(2);
    BigDecimal result;

    Invoice invoiceOne = testInvoiceProvider.invoiceOne();
    Invoice invoiceTwo = testInvoiceProvider.invoiceTwo();
    Invoice invoiceThree = testInvoiceProvider.invoiceThree();
    Invoice invoiceFour = testInvoiceProvider.invoiceFour();
    Invoice invoiceFive = testInvoiceProvider.invoiceFive();

    //when
    when(invoiceService.getInvoices()).thenReturn(Arrays.asList(invoiceOne, invoiceTwo, invoiceThree, invoiceFour, invoiceFive));
    result = taxCalculator.getVatDifference();

    //then
    assertEquals(expected, result);

  }
}