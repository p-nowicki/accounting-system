package pl.coderstrust.accounting;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderstrust.accounting.logic.InvoiceService;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class ApplicationTest2 {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceService invoceService;

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    Invoice invoice = new Invoice();

    invoice.setNumber("FV 324/12/2018");

    when(invoceService.getInvoices()).thenReturn(Arrays.asList(invoice));

    mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].number", is("FV 324/12/2018")));
  }

  private String toJson(Invoice invoice) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(invoice);
  }
}