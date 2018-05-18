package pl.coderstrust.accounting.database.impl.sql.mapper;

import static pl.coderstrust.accounting.database.impl.sql.TableLabels.AGE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.ID;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.ISSUE_DATE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NUMBER;

import pl.coderstrust.accounting.builders.InvoiceBuilder;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResultSetToInvoiceMapper {

  public static Invoice buildInvoice(ResultSet resultSet, List<InvoiceEntry> entryList) throws SQLException {
    return new InvoiceBuilder()
        .id(resultSet.getInt(ID))
        .buyer(ResultSetToCompanyMapper.getCustomer(resultSet))
        .seller(ResultSetToCompanyMapper.getSupplier(resultSet))
        .issueDate(LocalDate.parse(resultSet.getString(ISSUE_DATE), DateTimeFormatter.ISO_DATE))
        .entries(entryList)
        .age(resultSet.getInt(AGE))
        .name(resultSet.getString(NAME))
        .number(resultSet.getString(NUMBER))
        .build();
  }
}
