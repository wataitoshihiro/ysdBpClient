package io.pivotal.pal.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.sql.DataSource;
import java.util.TimeZone;

@SpringBootApplication
//@EnableAspectJAutoProxy
public class InvoiceApplication {

    public static void main(String[] args) {
        // Make sure the application runs as UTC
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
        SpringApplication.run(InvoiceApplication.class, args);
    }

//    @Bean
//    TestService testService(){
//        return new TestServiceImpl();
//    }

//    @Bean
//    InvoiceRepository invoiceRepository(DataSource dataSource) {
//        return new InvoiceRepositoryImpl(dataSource);
//    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .modules(new JavaTimeModule())
                .build();
    }

//    @Bean
//    EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
//        return (container) -> {
//            TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
//            factory.addConnectorCustomizers(
//                    (connector) -> {
//                        Http11NioProtocol handler = (Http11NioProtocol) connector.getProtocolHandler();
////                        handler.setAcceptCount(1);
//                        handler.setAcceptorThreadCount(1);
//                        handler.setMaxConnections(2);
//                        handler.setMaxThreads(1);
//                    }
//            );
//        };
//    }

}
