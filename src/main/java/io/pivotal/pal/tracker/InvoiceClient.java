package io.pivotal.pal.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

public class InvoiceClient {
    private static Logger logger = LoggerFactory.getLogger(InvoiceClient.class);
    private String invoiceUrl;
    private RestOperations restOperations;

    public InvoiceClient(String invoiceUrl, RestOperations restOperations) {
        this.invoiceUrl = invoiceUrl;
        this.restOperations = restOperations;
    }

    public Invoice find(Integer invoiceNumber) {
        logger.debug("invoiceUrl[{}{}{}]", invoiceUrl, "/", invoiceNumber);
        return restOperations.getForEntity(invoiceUrl + "/" + invoiceNumber, Invoice.class).getBody();
    }

}
