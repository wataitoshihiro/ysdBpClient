package io.pivotal.pal.tracker;

import java.util.List;

public interface InvoiceRepository {
    Invoice find(Integer invoiceNumber);

//    Invoice create(TimeEntry timeEntry);
//    List<Invoice> list();
//    Invoice update(Long id, TimeEntry timeEntry);
//    void delete(Long id);
}
