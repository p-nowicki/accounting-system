package pl.coderstrust.accounting.database.impl.sql.mapper;

import static pl.coderstrust.accounting.database.impl.sql.TableLabels.CITY_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.CITY_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NAME_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.NIP_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.POSTAL_CODE_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.POSTAL_CODE_SELLER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.STREET_BUYER;
import static pl.coderstrust.accounting.database.impl.sql.TableLabels.STREET_SELLER;

import pl.coderstrust.accounting.builders.CompanyBuilder;
import pl.coderstrust.accounting.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToCompanyMapper {

  public static Company getCustomer(ResultSet resultSet) throws SQLException {
    return new CompanyBuilder()
        .name(resultSet.getString(NAME_BUYER))
        .nip(resultSet.getInt(NIP_BUYER))
        .street(resultSet.getString(STREET_BUYER))
        .city(resultSet.getString(CITY_BUYER))
        .postalCode(resultSet.getString(POSTAL_CODE_BUYER))
        .build();
  }

  public static Company getSupplier(ResultSet resultSet) throws SQLException {
    return new CompanyBuilder()
        .name(resultSet.getString(NAME_SELLER))
        .nip(resultSet.getInt(NIP_SELLER))
        .street(resultSet.getString(STREET_SELLER))
        .city(resultSet.getString(CITY_SELLER))
        .postalCode(resultSet.getString(POSTAL_CODE_SELLER))
        .build();
  }
}
