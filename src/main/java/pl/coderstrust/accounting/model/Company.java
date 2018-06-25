package pl.coderstrust.accounting.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "company")
public class Company {

  @NotBlank
  private String name;

  @NotNull
  private int nip;

  @NotBlank
  private String street;

  @NotBlank
  private String postCode;

  @NotBlank
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
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Company company = (Company) other;
    return nip == company.nip
        && Objects.equals(name, company.name)
        && Objects.equals(street, company.street)
        && Objects.equals(postCode, company.postCode)
        && Objects.equals(city, company.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, nip, street, postCode, city);
  }

  @Override
  public String toString() {
    return "Company{"
        + "name='" + name + '\''
        + ", nip=" + nip
        + ", street='" + street + '\''
        + ", postCode='" + postCode + '\''
        + ", city='" + city + '\''
        + '}';
  }
}
