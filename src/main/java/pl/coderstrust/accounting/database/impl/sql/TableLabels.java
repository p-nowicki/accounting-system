package pl.coderstrust.accounting.database.impl.sql;

public enum TableLabels {

  INVOICE_ENTRY("\"Invoice_Entry\""),
  DESCRIPTION("description"),
  PRICE("price"),
  INVOICE_ID("invoice_id"),

  COMPANY("\"Company\""),
  NIP("nip"),
  NAME_COMPANY("name"),
  STREET("street"),
  POSTAL_CODE("postal_code"),
  CITY("city"),

  NAME_BUYER("name_buyer"),
  STREET_BUYER("street_buyer"),
  POSTAL_CODE_BUYER("postal_code_buyer"),
  CITY_BUYER("city_buyer"),

  NAME_SELLER("name_seller"),
  STREET_SELLER("street_seller"),
  POSTAL_CODE_SELLER("postal_code_seller"),
  CITY_SELLER("city_seller"),

  INVOICE("\"Invoice\""),
  ID("id"),
  NAME("name"),
  NUMBER("number"),
  ISSUE_DATE("issue_date"),
  AGE("age"),
  NIP_BUYER("nip_buyer"),
  NIP_SELLER("nip_seller");

  private String label;

  TableLabels(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
