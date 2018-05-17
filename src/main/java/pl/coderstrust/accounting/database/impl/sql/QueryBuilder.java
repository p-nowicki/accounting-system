package pl.coderstrust.accounting.database.impl.sql;

import static pl.coderstrust.accounting.database.impl.sql.TableLabels.AGE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.CITY;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.COMPANY;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.DESCRIPTION;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.ID;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.INVOICE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.INVOICE_ENTRY;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.INVOICE_ID;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.ISSUE_DATE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME_COMPANY;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NUMBER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.POSTAL_CODE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.PRICE;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.STREET;

import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

public class QueryBuilder {

  public static final String QUERY_INVOICE_IDS = "SELECT * FROM " + INVOICE + " ORDER BY id DESC;";

  private static final String QUERY_GET = "SELECT invoice.id, invoice.age, invoice.name, invoice.number,"
      + "invoice.issue_date, "
      + "invoice.nip_buyer, "
      + "buyer.name AS name_buyer, "
      + "buyer.street AS street_buyer, "
      + "buyer.postal_code AS postal_code_buyer, "
      + "buyer.city AS city_buyer, "
      + "invoice.nip_seller, "
      + "seller.name AS name_seller, "
      + "seller.street AS street_seller, "
      + "seller.postal_code AS postal_code_seller, "
      + "seller.city AS city_seller "
      + "FROM " + INVOICE + " invoice "
      + "INNER JOIN " + COMPANY + " buyer ON invoice.nip_buyer = buyer.nip\n"
      + "INNER JOIN " + COMPANY + " seller ON invoice.nip_seller = seller.nip";

  public static final String QUERY_INVOICE = QUERY_GET + " WHERE invoice.id = ?;";

  public static final String QUERY_ALL_INVOICES = QUERY_GET + ";";

  public static final String QUERY_DELETE_ENTRIES = "DELETE FROM " + INVOICE_ENTRY + " WHERE " + INVOICE_ID + " = ?;";

  public static final String QUERY_DELETE_INVOICE = "DELETE FROM " + INVOICE + " WHERE " + ID + " = ?;";

  public static final String QUERY_INVOICE_ENTRIES = "SELECT * FROM " + INVOICE_ENTRY + " WHERE " + ID + " = ?;";

  public static final String QUERY_NIP_NUMBERS = "SELECT " + NIP + " FROM " + COMPANY;

  public static String getInsertInvoiceQuery(Invoice invoice) {
    return "INSERT INTO " + INVOICE + " (" + ISSUE_DATE + "," + AGE
        + "," + NIP_BUYER + "," + NIP_SELLER + "," + NAME + "," + NUMBER
        + ") VALUES ("
        + "'" + invoice.getIssueDate() + "', "
        + "'" + invoice.getAge() + "', "
        + "'" + invoice.getBuyer().getNip() + "', "
        + "'" + invoice.getSeller().getNip() + "', "
        + "'" + invoice.getName() + "', "
        + "'" + invoice.getNumber() + "'" + ");";
  }

  public static String getInsertCompanyQuery(Company company) {
    return "INSERT INTO " + COMPANY + " VALUES ("
        + "'" + company.getName() + "', "
        + "'" + company.getStreet() + "', "
        + "'" + company.getPostCode() + "', "
        + "'" + company.getCity() + "', "
        + "'" + company.getNip() + "');";
  }

  public static String getInsertEntryQuery(int id, InvoiceEntry invoiceEntry) {
    return "INSERT INTO " + INVOICE_ENTRY + "(" + DESCRIPTION + "," + PRICE + ","
        + INVOICE_ID
        + ") VALUES ("
        + "'" + invoiceEntry.getDescription() + "', "
        + "'" + invoiceEntry.getPrice() + "', "
        + "'" + id + "');";
  }

  public static String getUpdateInvoiceQuery(Invoice invoice) {
    return "UPDATE " + INVOICE + "SET "
        + ISSUE_DATE + "='" + invoice.getIssueDate() + "', "
        + NIP_BUYER + "='" + invoice.getBuyer().getNip() + "', "
        + NIP_SELLER + "='" + invoice.getSeller().getNip() + "', "
        + NAME + "='" + invoice.getName() + "', "
        + AGE + "='" + invoice.getAge() + "'"
        + " WHERE " + ID + "='" + invoice.getId() + "';";
  }

  public static String getUpdateEntryQuery(int id, InvoiceEntry invoiceEntry) {
    return "UPDATE " + INVOICE_ENTRY + " SET "
        + DESCRIPTION + "='" + invoiceEntry.getDescription() + "', "
        + PRICE + "='" + invoiceEntry.getPrice() + "', "
        + INVOICE_ID + "='" + id + "'"
        + " WHERE " + INVOICE_ID + "=" + id + ";";
  }

  public static String getUpdateCompanyQuery(Company company) {
    return "UPDATE " + COMPANY + " SET "
        + NAME_COMPANY + "='" + company.getName() + "', "
        + STREET + "='" + company.getStreet() + "', "
        + POSTAL_CODE + "='" + company.getPostCode() + "', "
        + CITY + "='" + company.getCity() + "'"
        + " WHERE " + NIP + "=" + company.getNip() + ";";
  }
}
