package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * An account repository that uses JPA to find accounts.
 */
//@Repository("invoiceRepository")
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public Invoice find(Integer invoiceNumber) {
		String jpql = "SELECT a FROM Invoice a where a.invoiceNumber = :invoiceNumber";
		Invoice invoice = entityManager.createQuery(jpql, Invoice.class)
				.setParameter("invoiceNumber", invoiceNumber).getSingleResult();
		return invoice;
	}

    public Invoice findByKey(Integer invoiceNumber) {
        String jpql = "SELECT a FROM Invoice a where a.invoiceNumber = :invoiceNumber";
        Invoice invoice = entityManager.createQuery(jpql, Invoice.class)
                .setParameter("invoiceNumber", invoiceNumber).getSingleResult();
        return invoice;
    }

}
