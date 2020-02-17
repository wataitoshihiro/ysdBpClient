package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {

    @Value("${invoice-repo.url}") String invoiceRepoUrl;

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public InvoiceClient invoiceClient(RestOperations restOperations) {
        return new InvoiceClient(invoiceRepoUrl, restOperations);
    }

}
