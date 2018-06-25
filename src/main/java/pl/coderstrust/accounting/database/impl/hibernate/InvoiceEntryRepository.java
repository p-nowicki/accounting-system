package pl.coderstrust.accounting.database.impl.hibernate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.model.InvoiceEntry;

@Repository
public interface InvoiceEntryRepository extends CrudRepository<InvoiceEntry, Integer> {
}