package io.pivotal.pal.tracker;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Loads account aggregates. Called by the reward network to find and
 * reconstitute Account entities from an external form such as a set of RDMS
 * rows.
 * 
 * Objects returned by this repository are guaranteed to be fully initialized
 * and ready to use.
 */
//public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {
public interface InvoiceRepository 
	extends CrudRepository<Invoice, Integer>, InvoiceRepositoryCustom {
	@Query("SELECT a FROM Invoice a where a.invoiceNumber = :invoiceNumber")
	public Invoice findByPrimaryKey(Integer invoiceNumber);
	public Optional<Invoice> findByInvoiceNumber(Integer invoiceNumber);
	public List<Invoice> findBySize(Integer size);
//	public List<Invoice> findByWeightLessThan(Integer w);
//	public List<Invoice> findByWeightBetween(Integer w1, Integer w2);
}
