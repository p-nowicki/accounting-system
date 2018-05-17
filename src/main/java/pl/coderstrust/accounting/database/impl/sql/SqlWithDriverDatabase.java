package pl.coderstrust.accounting.database.impl.sql;

import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.QUERY_ALL_INVOICES;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.QUERY_DELETE_ENTRIES;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.QUERY_DELETE_INVOICE;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.QUERY_INVOICE;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.QUERY_INVOICE_ENTRIES;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.QUERY_INVOICE_IDS;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.QUERY_NIP_NUMBERS;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.getInsertCompanyQuery;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.getInsertEntryQuery;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.getInsertInvoiceQuery;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.getUpdateCompanyQuery;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.getUpdateEntryQuery;
import static pl.coderstrust.accounting.database.impl.sql.QueryBuilder.getUpdateInvoiceQuery;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.DESCRIPTION;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.ID;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.PRICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.impl.sql.mapper.ResultSetToInvoiceMapper;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class SqlWithDriverDatabase implements Database {

  Connection connection;

  @Autowired
  public SqlWithDriverDatabase() throws SQLException {
    connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/coders", "postgres",
        "postgres");
  }

  @Override
  public synchronized int saveInvoice(Invoice invoice) {
    addCompanyIfNotExists(invoice.getBuyer());
    addCompanyIfNotExists(invoice.getSeller());

    executeStatement(getInsertInvoiceQuery(invoice));

    invoice.setId(getInvoiceIds().get(0));

    for (InvoiceEntry invoiceEntry : invoice.getEntries()) {
      String queryEntry = getInsertEntryQuery(invoice.getId(), invoiceEntry);
      executeStatement(queryEntry);
    }

    return invoice.getId();
  }

  @Override
  public Invoice getInvoiceById(int id) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INVOICE)) {
      preparedStatement.setInt(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next() == false) {
          return null;
        }
        return buildInvoice(resultSet);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public void updateInvoice(int id, Invoice invoice) {
    addOrUpdateCompany(invoice.getBuyer());
    addOrUpdateCompany(invoice.getSeller());

    String queryInvoice = getUpdateInvoiceQuery(invoice);
    executeStatement(queryInvoice);

    for (InvoiceEntry invoiceEntry : invoice.getEntries()) {
      String queryEntry = getUpdateEntryQuery(invoice.getId(), invoiceEntry);
      executeStatement(queryEntry);
    }
  }

  @Override
  public List<Invoice> getInvoices() {
    List<Invoice> invoiceList = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {
      try (ResultSet resultSet = statement.executeQuery(QUERY_ALL_INVOICES)) {
        if (!resultSet.next()) {
          return new ArrayList<>();
        }
        do {
          invoiceList.add(buildInvoice(resultSet));
        } while (resultSet.next());
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return invoiceList;
  }

  @Override
  public void removeInvoiceById(int id) {
    getPreparedStatementWithParameter(id, QUERY_DELETE_ENTRIES);
    getPreparedStatementWithParameter(id, QUERY_DELETE_INVOICE);
  }

  private List<Integer> getInvoiceIds() {
    List<Integer> invoiceIds = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {
      try (ResultSet resultSet = statement.executeQuery(QUERY_INVOICE_IDS)) {
        while (resultSet.next()) {
          invoiceIds.add(resultSet.getInt(ID));
        }
      }
    } catch (SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    return invoiceIds;
  }

  private List<InvoiceEntry> getEntryList(int id) throws SQLException {
    List<InvoiceEntry> entryList;

    try (PreparedStatement preparedStatementEntries = connection.prepareStatement(QUERY_INVOICE_ENTRIES)) {
      preparedStatementEntries.setInt(1, id);
      try (ResultSet resultSet = preparedStatementEntries.executeQuery()) {
        entryList = new ArrayList<>();
        while (resultSet.next()) {
          String description = resultSet.getString(DESCRIPTION);
          BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(PRICE));
          InvoiceEntry invoiceEntry = new InvoiceEntry(description, price);
          entryList.add(invoiceEntry);
        }
      }
    }
    return entryList;
  }

  private void addOrUpdateCompany(Company company) {
    if (!getCompaniesNipNumbers().contains(company.getNip())) {
      addCompany(company);
    } else {
      updateCompany(company);
    }
  }

  private void addCompanyIfNotExists(Company company) {
    if (!getCompaniesNipNumbers().contains(company.getNip())) {
      addCompany(company);
    }
  }

  private void addCompany(Company company) {
    String queryCustomer = getInsertCompanyQuery(company);
    executeStatement(queryCustomer);
  }

  private void updateCompany(Company company) {
    executeStatement(getUpdateCompanyQuery(company));
  }

  private void executeStatement(String query) {
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(query);
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  private void getPreparedStatementWithParameter(int id, String queryEntries) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(queryEntries)) {
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  private List<Integer> getCompaniesNipNumbers() {
    List<Integer> companiesNipNumbers = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {
      try (ResultSet resultSet = statement.executeQuery(QUERY_NIP_NUMBERS)) {
        while (resultSet.next()) {
          companiesNipNumbers.add(resultSet.getInt(NIP));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException(ex.getMessage());
    }
    return companiesNipNumbers;
  }

  private Invoice buildInvoice(ResultSet resultSet) throws SQLException {
    List<InvoiceEntry> entryList = getEntryList(resultSet.getInt(ID));
    return ResultSetToInvoiceMapper.buildInvoice(resultSet, entryList);
  }
}
