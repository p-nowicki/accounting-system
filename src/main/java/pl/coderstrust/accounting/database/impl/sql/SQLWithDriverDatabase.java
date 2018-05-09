package pl.coderstrust.accounting.database.impl.sql;

import static pl.coderstrust.accounting.database.impl.sql.TableLabels.AGE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.CITY_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.CITY_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.COMPANY;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.DESCRIPTION;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.ID;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.INVOICE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.INVOICE_ENTRY;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.INVOICE_ID;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.ISSUE_DATE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NUMBER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.POSTAL_CODE_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.POSTAL_CODE_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.PRICE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.STREET_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.STREET_SELLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;
import pl.coderstrust.accounting.objectBuilders.CompanyBuilder;
import pl.coderstrust.accounting.objectBuilders.InvoiceBuilder;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class SQLWithDriverDatabase implements Database {

  Connection connection;

  @Autowired
  public SQLWithDriverDatabase() throws SQLException {
    connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/coders", "postgres",
        "postgres");
  }

  @Override
  public synchronized int saveInvoice(Invoice invoice) {

    if (!getCompaniesNipNumbers().contains(invoice.getBuyer().getNip())) {
      String queryBuyer = getBuyerQuery(invoice);
      getStatement(queryBuyer);
    }

    if (!getCompaniesNipNumbers().contains(invoice.getSeller().getNip())) {
      String querySeller = getSellerQuery(invoice);
      getStatement(querySeller);
    }

    String queryInvoice = getInsertInvoiceQuery(invoice);
    getStatement(queryInvoice);

    int id;
    String queryInvoiceId = "SELECT * FROM " + INVOICE.getLabel() + " ORDER BY id DESC LIMIT 1";
    try (PreparedStatement preparedStatement = connection.prepareStatement(queryInvoiceId)) {
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        resultSet.next();
        id = resultSet.getInt(ID.getLabel());
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("query id:" + e.getMessage());
    }

    for (InvoiceEntry invoiceEntry : invoice.getEntries()) {
      String queryEntry = getInsertEntryQuery(id, invoiceEntry);
      getStatement(queryEntry);
    }

    invoice.setId(id);
    return id;
  }

  @Override
  public Invoice getInvoiceById(int id) {
    String query = "SELECT i.id, i.age, i.name, i.number, i.issue_date, \n"
        + "i.nip_buyer, c.name AS name_buyer, c.street AS street_buyer, c.postal_code AS postal_code_buyer,"
        + "c.city AS city_buyer,\n"
        + "i.nip_seller, s.name AS name_seller, s.street AS street_seller, s.postal_code AS postal_code_seller,"
        + "s.city AS city_seller \n"
        + "FROM " + INVOICE.getLabel() + " i\n"
        + "INNER JOIN " + COMPANY.getLabel() + " c ON i.nip_buyer = c.nip\n"
        + "INNER JOIN " + COMPANY.getLabel() + " s ON i.nip_seller = s.nip "
        + "WHERE i.id = ?;";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next() == false) {
          return null;
        }
        return getInvoice(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void updateInvoice(int id, Invoice invoice) {

    //TODO queries to update
    if (!getCompaniesNipNumbers().contains(invoice.getBuyer().getNip())) {
      String queryCustomer = getBuyerQuery(invoice);
      getStatement(queryCustomer);
    }
    if (!getCompaniesNipNumbers().contains(invoice.getSeller().getNip())) {
      String querySupplier = getSellerQuery(invoice);
      getStatement(querySupplier);
    }

    String queryInvoice = getUpdateInvoiceQuery(invoice);
    getStatement(queryInvoice);

    for (InvoiceEntry invoiceEntry : invoice.getEntries()) {
      String queryEntry = getUpdateEntryQuery(invoice.getId(), invoiceEntry);
      getStatement(queryEntry);
    }
  }

  @Override
  public List<Invoice> getInvoices() {
    String query = "SELECT i.id, i.age, i.name, i.number, i.issue_date, \n"
        + "i.nip_buyer, c.name AS name_buyer, c.street AS street_buyer, c.postal_code AS postal_code_buyer,"
        + "c.city AS city_buyer,\n"
        + "i.nip_seller, s.name AS name_seller, s.street AS street_seller, s.postal_code AS postal_code_seller,"
        + "s.city AS city_seller \n"
        + "FROM " + INVOICE.getLabel() + " i\n"
        + "INNER JOIN " + COMPANY.getLabel() + " c ON i.nip_buyer = c.nip\n"
        + "INNER JOIN " + COMPANY.getLabel() + " s ON i.nip_seller = s.nip;";
    List<Invoice> invoiceList = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {
      try (ResultSet resultSet = statement.executeQuery(query)) {
        if (!resultSet.next()) {
          return new ArrayList<>();
        }
        do {
          invoiceList.add(getInvoice(resultSet));
        } while (resultSet.next());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return invoiceList;
  }

  @Override
  public void removeInvoiceById(int id) {
    String queryEntries = "DELETE FROM " + INVOICE_ENTRY.getLabel() + " WHERE " + INVOICE_ID.getLabel() + " = ?;";
    getPreparedStatementWithParameter(id, queryEntries);

    String queryInvoice = "DELETE FROM " + INVOICE.getLabel() + " WHERE " + ID.getLabel() + " = ?;";
    getPreparedStatementWithParameter(id, queryInvoice);
  }

  private Invoice getInvoice(ResultSet resultSet) throws SQLException {
    Company buyer = new CompanyBuilder()
        .name(resultSet.getString(NAME_BUYER.getLabel()))
        .nip(resultSet.getInt(NIP_BUYER.getLabel()))
        .street(resultSet.getString(STREET_BUYER.getLabel()))
        .city(resultSet.getString(CITY_BUYER.getLabel()))
        .postalCode(resultSet.getString(POSTAL_CODE_BUYER.getLabel()))
        .build();

    Company seller = new CompanyBuilder()
        .name(resultSet.getString(NAME_SELLER.getLabel()))
        .nip(resultSet.getInt(NIP_SELLER.getLabel()))
        .street(resultSet.getString(STREET_SELLER.getLabel()))
        .city(resultSet.getString(CITY_SELLER.getLabel()))
        .postalCode(resultSet.getString(POSTAL_CODE_SELLER.getLabel()))
        .build();

    return new InvoiceBuilder()
        .id(resultSet.getInt(ID.getLabel()))
        .number(resultSet.getString(NUMBER.getLabel()))
        .name(resultSet.getString(NAME.getLabel()))
        .buyer(buyer)
        .seller(seller)
        .issueDate(LocalDate.parse(resultSet.getString(ISSUE_DATE.getLabel()), DateTimeFormatter.ISO_DATE))
        .entries(getEntryList(resultSet.getInt(ID.getLabel())))
        .build();
  }

  private List<InvoiceEntry> getEntryList(int id) throws SQLException {
    String queryEntries = "SELECT * FROM " + INVOICE_ENTRY.getLabel() + " WHERE " + ID.getLabel() + " = ?;";
    List<InvoiceEntry> entryList;

    try (PreparedStatement preparedStatementEntries = connection.prepareStatement(queryEntries)) {
      preparedStatementEntries.setInt(1, id);
      try (ResultSet resultSet = preparedStatementEntries.executeQuery()) {
        entryList = new ArrayList<>();
        while (resultSet.next()) {
          String description = resultSet.getString(DESCRIPTION.getLabel());
          BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(PRICE.getLabel()));
          InvoiceEntry invoiceEntry = new InvoiceEntry(description, price);
          entryList.add(invoiceEntry);
        }
      }
    }
    return entryList;
  }

  private String getInsertInvoiceQuery(Invoice invoice) {
    return new StringBuilder(
        "INSERT INTO " + INVOICE.getLabel() + " (" + ISSUE_DATE.getLabel() + "," + AGE.getLabel()
            + "," + NIP_BUYER.getLabel() + "," + NIP_SELLER.getLabel() + "," + NAME.getLabel() + "," + NUMBER.getLabel()
            + ") VALUES (")
        .append("'" + invoice.getIssueDate() + "', ")
        .append("'" + invoice.getAge() + "', ")
        .append("'" + invoice.getBuyer().getNip() + "', ")
        .append("'" + invoice.getSeller().getNip() + "', ")
        .append("'" + invoice.getName() + "', ")
        .append("'" + invoice.getNumber() + "'" + ");")
        .toString();
  }

  private String getSellerQuery(Invoice invoice) {
    return new StringBuilder("INSERT INTO " + COMPANY.getLabel() + " VALUES (")
        .append("'" + invoice.getSeller().getName() + "', ")
        .append("'" + invoice.getSeller().getStreet() + "', ")
        .append("'" + invoice.getSeller().getPostCode() + "', ")
        .append("'" + invoice.getSeller().getCity() + "', ")
        .append("'" + invoice.getSeller().getNip() + "');")
        .toString();
  }

  private String getBuyerQuery(Invoice invoice) {
    return new StringBuilder("INSERT INTO " + COMPANY.getLabel() + " VALUES (")
        .append("'" + invoice.getBuyer().getName() + "', ")
        .append("'" + invoice.getBuyer().getStreet() + "', ")
        .append("'" + invoice.getBuyer().getPostCode() + "', ")
        .append("'" + invoice.getBuyer().getCity() + "', ")
        .append("'" + invoice.getBuyer().getNip() + "');")
        .toString();
  }

  private String getInsertEntryQuery(int id, InvoiceEntry invoiceEntry) {
    return new StringBuilder(
        "INSERT INTO " + INVOICE_ENTRY.getLabel() + "(" + DESCRIPTION.getLabel() + "," + PRICE.getLabel() + ","
            + INVOICE_ID.getLabel()
            + ") VALUES (")
        .append("'" + invoiceEntry.getDescription() + "', ")
        .append("'" + invoiceEntry.getPrice() + "', ")
        .append("'" + id + "');")
        .toString();
  }

  private String getUpdateInvoiceQuery(Invoice invoice) {
    return new StringBuilder(
        "UPDATE " + INVOICE.getLabel() + "SET ")
        .append(ISSUE_DATE.getLabel() + "='" + invoice.getIssueDate() + "', ")
        .append(NIP_BUYER.getLabel() + "='" + invoice.getBuyer().getNip() + "', ")
        .append(NIP_SELLER.getLabel() + "='" + invoice.getSeller().getNip() + "', ")
        .append(NAME.getLabel() + "='" + invoice.getName() + "', ")
        .append(AGE.getLabel() + "='" + invoice.getAge() + "'")
        .append(" WHERE " + ID.getLabel() + "=" + invoice.getId() + ";")
        .toString();
  }

  private String getUpdateEntryQuery(int id, InvoiceEntry invoiceEntry) {
    return new StringBuilder(
        "UPDATE " + INVOICE_ENTRY.getLabel() + " SET ")
        .append(DESCRIPTION.getLabel() + "='" + invoiceEntry.getDescription() + "', ")
        .append(PRICE.getLabel() + "='" + invoiceEntry.getPrice() + "', ")
        .append(INVOICE_ID.getLabel() + "='" + id + "'")
        .append(" WHERE " + INVOICE_ID.getLabel() + "=" + id + ";")
        .toString();
  }

  private void getStatement(String query) {
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private void getPreparedStatementWithParameter(int id, String queryEntries) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(queryEntries)) {
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private List<Integer> getCompaniesNipNumbers() {
    String query = "SELECT " + NIP.getLabel() + " FROM " + COMPANY.getLabel();
    List<Integer> companiesNipNumbers = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {
      try (ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
          companiesNipNumbers.add(resultSet.getInt(NIP.getLabel()));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IllegalArgumentException(e.getMessage());
    }
    return companiesNipNumbers;
  }
}
