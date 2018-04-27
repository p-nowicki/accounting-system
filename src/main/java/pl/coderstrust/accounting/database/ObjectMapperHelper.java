package pl.coderstrust.accounting.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;

public final class ObjectMapperHelper {

  private final ObjectMapper mapper;

  public ObjectMapperHelper() {
    this.mapper = new ObjectMapper();
    this.mapper.registerModule(new JavaTimeModule());
  }

  public String convertInvoiceToJson(Invoice invoice) {
    try {
      return mapper.writeValueAsString(invoice);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException(ex);
    }
  }

  public Invoice convertJsonToInvoice(String invoiceAsString) {
    try {
      return mapper.readValue(invoiceAsString, Invoice.class);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}