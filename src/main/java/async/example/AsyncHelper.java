package async.example;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

@Component
public class AsyncHelper {

    @Async
    public void streaming(ResponseBodyEmitter emitter, long eventNumber, long intervalSec) throws Exception {
        System.out.println("Start Async processing.");

        for (long i = 1; i <= eventNumber; i++) {
            System.out.println("Start send");
            emitter.send("msg" + i + "\r\n");
            System.out.println("End   send");
            Thread.sleep(intervalSec);
        }

        emitter.complete();

        System.out.println("End Async processing.");
    }

}