package pl.coderstrust.accounting.builders;

import pl.coderstrust.accounting.model.Company;

public class CompanyBuilder {

  private Company company;

  public CompanyBuilder() {
    company = new Company();
  }

  public CompanyBuilder street(String street) {
    company.setStreet(street);
    return this;
  }

  public CompanyBuilder city(String city) {
    company.setCity(city);
    return this;
  }

  public CompanyBuilder name(String name) {
    company.setName(name);
    return this;
  }

  public CompanyBuilder postalCode(String postalCode) {
    company.setPostCode(postalCode);
    return this;
  }

  public CompanyBuilder nip(int nip) {
    company.setNip(nip);
    return this;
  }

  public Company build() {
    return company;
  }
}
