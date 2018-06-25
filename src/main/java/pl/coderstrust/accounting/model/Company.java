package pl.coderstrust.accounting.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "company")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
    return id == company.id &&
        nip == company.nip &&
        Objects.equals(name, company.name) &&
        Objects.equals(street, company.street) &&
        Objects.equals(postCode, company.postCode) &&
        Objects.equals(city, company.city);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, nip, street, postCode, city);
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
