package pl.coderstrust.accounting.model;

import java.util.Objects;

public class Company {

  private String name;
  private int nip;
  private String street;
  private String postCode;
  private String city;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNip() {
    return nip;
  }

  public void setNip(int nip) {
    this.nip = nip;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Company company = (Company) o;
    return nip == company.nip &&
        Objects.equals(name, company.name) &&
        Objects.equals(street, company.street) &&
        Objects.equals(postCode, company.postCode) &&
        Objects.equals(city, company.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, nip, street, postCode, city);
  }
}
