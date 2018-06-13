package pl.coderstrust.accounting.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest3 {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    Invoice invoice = new Invoice();
    invoice.setNumber("FV 324/12/2018");

    mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));

  }

  @Test
  public void shouldReturnDefaultMessage2() throws Exception {
    Invoice invoice = new Invoice();
    invoice.setNumber("FV 324/12/2018");

    mockMvc
        .perform(
            post("/invoices")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJson(invoice))
        )
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].number", is("FV 324/12/2018")));

  }

  @Test
  public void shouldReturnDefaultMessage3() throws Exception {
    Invoice invoice = new Invoice();
    invoice.setNumber("FV 324/12/2018");
    Invoice invoice1 = new Invoice();
    invoice1.setNumber("FV 233/10/2019");

    mockMvc
        .perform(
            post("/invoices")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJson(invoice))
        )
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].number", is("FV 324/12/2018")));

    mockMvc
        .perform(
            put("/invoices/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJson(invoice1))
        )
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].number", is("FV 233/10/2019")));

  }

  @Test
  public void shouldReturnDefaultMessage4() throws Exception {
    Invoice invoice = new Invoice();
    invoice.setNumber("FV 324/12/2018");

    mockMvc
        .perform(
            post("/invoices")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJson(invoice))
        )
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].number", is("FV 324/12/2018")));

    mockMvc
        .perform(
            delete("/invoices/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(toJson(invoice))
        )
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));

  }

  private String toJson(Invoice invoice) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(invoice);
  }
}