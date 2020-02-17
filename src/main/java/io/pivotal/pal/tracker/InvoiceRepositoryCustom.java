package io.pivotal.pal.tracker;

public interface InvoiceRepositoryCustom {
	public Invoice findByKey(Integer invoiceNumber) ;
    public Invoice find(Integer invoiceNumber);
}