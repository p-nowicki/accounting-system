package pl.coderstrust.accounting.database.impl.hibernate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.model.Company;

import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {

  Optional<Company> findByNip(int nip);

//  @Transactional(rollbackOn = Exception.class)
//  @Modifying
//  @Query("update company c set "
//      + "c.name = :name, "
//      + "c.phoneNumber = :phoneNumber, "
//      + "c.bankAccount = :bankAccount, "
//      + "address.street = :street, "
//      + "address.postalCode = :postalCode, "
//      + "address.city = :city, "
//      + "address.country = :country "
//      + "where c.vatIdentificationNumber = :vatIdentificationNumber")
//  void updateByVatIdentificationNumber(
//      @Param("name") String name,
//      @Param("phoneNumber") int phoneNumber,
//      @Param("bankAccount") String bankAccount,
//      @Param("street") String street,
//      @Param("postalCode") String postalCode,
//      @Param("city") String city,
//      @Param("country") String country,
//      @Param("vatIdentificationNumber") String vatIdentificationNumber);
}