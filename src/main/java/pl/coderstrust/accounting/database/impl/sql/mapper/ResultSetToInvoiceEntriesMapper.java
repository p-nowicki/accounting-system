package pl.coderstrust.accounting.database.impl.sql.mapper;

import static pl.coderstrust.accounting.database.impl.sql.TableLabels.DESCRIPTION;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.PRICE;

import pl.coderstrust.accounting.model.InvoiceEntry;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToInvoiceEntriesMapper {
  public static InvoiceEntry getInvoiceEntry(ResultSet resultSet) throws SQLException {
    String description = resultSet.getString(DESCRIPTION);
    BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(PRICE));
    return new InvoiceEntry(description, price);
  }
}
