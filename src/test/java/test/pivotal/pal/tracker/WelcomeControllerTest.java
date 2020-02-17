package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.InvoiceClient;
import io.pivotal.pal.tracker.InvoiceRepository;
import io.pivotal.pal.tracker.WelcomeController;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WelcomeControllerTest {

    private InvoiceClient albumsClient;
    private InvoiceRepository invoiceRepo;

    @Test
    public void itSaysHello() throws Exception {
        WelcomeController controller = new WelcomeController(
                "A welcome message",
                albumsClient,
                invoiceRepo);

        assertThat(controller.sayHello()).isEqualTo("A welcome message");
    }
}
