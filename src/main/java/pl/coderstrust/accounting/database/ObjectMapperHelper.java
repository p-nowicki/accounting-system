package pl.coderstrust.accounting.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;

public final class ObjectMapperHelper {

  private ObjectMapper mapper;

  public ObjectMapperHelper(ObjectMapper objectMapper) {
    this.mapper = objectMapper;
    this.mapper.registerModule(new JavaTimeModule());
  }

  public String convertInvoiceToJson(Invoice invoice) throws JsonProcessingException {
    return mapper.writeValueAsString(invoice);
  }

  public Invoice convertJsonToInvoice(String invoiceAsString) throws IOException {
    return mapper.readValue(invoiceAsString, Invoice.class);
  }
}