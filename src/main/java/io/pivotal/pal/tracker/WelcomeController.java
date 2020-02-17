package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.thread.ChildThread;
import io.pivotal.pal.tracker.thread.InvoiceRepoMsaChildThread;
import io.pivotal.pal.tracker.thread.ThreadLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import tx.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {
    private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
    private final InvoiceClient invoiceClient;
    private final InvoiceRepository invoiceRepo;
    
//    private String welcomeMessage;
//    private String welcomeMessage="hello !!!";

    public WelcomeController(
//        @Value("${welcome_message}") String welcomeMessage,
        InvoiceClient invoiceClient,
        InvoiceRepository invoiceRepo
    ) {
//        this.welcomeMessage = welcomeMessage;
        this.invoiceClient = invoiceClient;
        this.invoiceRepo = invoiceRepo;
    }

//    public WelcomeController(){}

    @GetMapping("/")
//    public String sayHello() {
//        return welcomeMessage;
//    }
    public String sayHello() {
        return "hello !!! ->";
    }

    //JDBC実験用
    @RequestMapping(value = "/invoice" , method = RequestMethod.GET)
    public ResponseEntity<Invoice> invoice(
//            @RequestParam(defaultValue = "1") Long id,
//            @RequestParam(defaultValue = "5") Integer multiple,
            @RequestParam(defaultValue = "500") Integer number
    ) {
        logger.info("■■■■■■ s t a r t ■■■■■■");
        logger.info("<<<invoice record by jdbc direct read >>> number[{}]", number);
        StopWatch sw = new StopWatch();
        sw.start();

        Integer searchNumber = 0;
        for (Integer i=1; i<=number; i++ ){
            searchNumber++;
            if (searchNumber == 501){searchNumber = 1;}
            Invoice invoice = invoiceRepo.find(searchNumber);
        }
        
        sw.stopReport("<<<invoice record by jdbc direct read >>>");
        Invoice invoice = invoiceRepo.find(1);
        logger.info("■■■■■■ e   n   d ■■■■■■");
        if (invoice != null) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
//            return new ResponseEntity<>(invoice, HttpStatus.PARTIAL_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //MSA実験用
    @RequestMapping(value = "/invoice-msa" , method = RequestMethod.GET)
    public ResponseEntity<Invoice> invoiceMsa(
//            @RequestParam(defaultValue = "1") Long id,
            @RequestParam(defaultValue = "5") Integer multiple,
            @RequestParam(defaultValue = "500") Integer number
    ) {
//        Long id = 1L;
        Integer searchNumber = 0;
        for (Integer i=1; i<=number; i++ ){
            searchNumber++;
            if (searchNumber == 501){searchNumber = 1;}
            Invoice invoice = invoiceClient.find(searchNumber);
        }
        Invoice invoice = invoiceClient.find(1);
        if (invoice != null) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //MSA実験用
    @RequestMapping(value = "/invoice-multi" , method = RequestMethod.GET)
    public String invoiceMultiple(
//            @RequestParam(defaultValue = "1") Long id,
            @RequestParam(defaultValue = "1") Integer multiple,
            @RequestParam(defaultValue = "500") Integer number
    ) {
        logger.info("■■■■■■ s t a r t ■■■■■■");
        logger.info("<<< invoice record by micro service(repository) >>> thread[{}], number[{}]", multiple, number);
        StopWatch sw = new StopWatch();
        sw.start();
        invoiceRepoMsaThreadStart(multiple, number);
        sw.stopReport("<<<invoice record by micro service(repository) >>>");
        logger.info("■■■■■■ e   n   d ■■■■■■");
        return "hello invoice-multiple(" + String.valueOf(multiple) + ", "+ String.valueOf(number) + ")";
    }

    private void invoiceRepoMsaThreadStart(Integer multiple, Integer number) {
        logger.debug("multiple[{}]", multiple);
        logger.debug("number[{}]", number);

        Integer numberForChild = number/multiple;
        Integer surplus = number%multiple;

        //        int childNum = 5 ;
        int childCnt = 0 ;
        childCnt = 0 ;
        for ( childCnt = 1 ; childCnt <= multiple ; childCnt++ ) {
            if (childCnt == multiple){
                numberForChild = numberForChild + surplus;
            }
            InvoiceRepoMsaChildThread thd = 
                    new InvoiceRepoMsaChildThread(lock, childCnt, numberForChild, invoiceClient);
            thd.start();
        }
        lock.lockWait();
    }
    
    // マルチスレッドの実験用
    @GetMapping("/hellolate")
    public String hellolate() throws Exception {
        StopWatch sw = new StopWatch();
        sw.start();
        logger.info("wait a seconds - start ");
        Thread.sleep(2000);
        sw.stopReport("### excute time ###");
        return "I'm sorry, I'm late";
    }
    // マルチスレッドの実験用
    @RequestMapping(value = "/hellomultiple" , method = RequestMethod.GET)
    public String hellomultiple(
            @RequestParam(defaultValue = "5") long multiple,
            @RequestParam(defaultValue = "20") long number
    ) {
        threadStart(multiple, number);
        return "hello multiple(" + String.valueOf(multiple) + ", "+ String.valueOf(number) + ")";
    }

    
//    static WelcomeController lock = new WelcomeController("");
    ThreadLock lock = new ThreadLock();
//    static int    lockCnt = 0 ;
    
    private void threadStart(long multiple, long number) {
        logger.debug("multiple[{}]", multiple);
        logger.debug("number[{}]", number);
        //        int childNum = 5 ;
        int childCnt = 0 ;
        childCnt = 0 ;
        for ( childCnt = 0 ; childCnt < multiple ; childCnt++ ) {
            ChildThread thd = new ChildThread(lock, childCnt);
            thd.start();
        }
        lock.lockWait();
    }

//    public synchronized static void lockAdd() {
//        lockCnt++ ;
//    }
//    public synchronized void lockDelete() {
//        lockCnt-- ;
//        notify();
//    }
//    public synchronized void lockWait() {
//        while ( lockCnt > 0 ) {
//            try {
//                logger.debug("suspend, lockCnt not 0 [{}]", lockCnt);
//                wait();
//            } catch (InterruptedException e) {
//                // TODO 自動生成された catch ブロック
//                e.printStackTrace();
//            }
//        }
//        logger.debug("resume, lockCnt [{}]", lockCnt);
//    }
//MSA実験用
@RequestMapping(value = "/persons/{id}" , method = RequestMethod.GET)
public Map<String, String> person(
        @PathVariable("id")  Long id
) {
    Map<String, String> map = new HashMap<>();
    map.put("samurai", "侍");
    map.put("engineer", "エンジニア");
    id = id * 10;
    map.put("id×10＝", id.toString());
    return map;
}

    //MSA実験用
    @RequestMapping(value = "/cars/{id1}-{id2}" , method = RequestMethod.GET)
    public Map<String, String> personId(
            @PathVariable("id1")  String id1,
            @PathVariable("id2")  String id2
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("samurai", "侍");
        map.put("engineer", "エンジニア");
        map.put("id1", id1.toString());
        map.put("id2", id2.toString());
        return map;
    }

    //MSA実験用
    @RequestMapping(value = "/cars/{id1}-{id2}:undelete" , method = RequestMethod.GET)
    public Map<String, String> personId2(
            @PathVariable("id1")  String id1,
            @PathVariable("id2")  String id2
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("TYTLE", "undelete");
        map.put("samurai", "侍");
        map.put("engineer", "エンジニア");
        map.put("id1", id1.toString());
        map.put("id2", id2.toString());
        return map;
    }

    //MSA実験用
    @RequestMapping(value = "/cars" , method = RequestMethod.GET)
    public Map<String, String> personId3(
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("TYTLE", "cars");
        map.put("samurai", "侍");
        map.put("engineer", "エンジニア");
        return map;
    }

    //MSA実験用
    @RequestMapping(value = "/cars:undelete" , method = RequestMethod.GET)
    public Map<String, String> personId4(
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("TYTLE", "cars:undelete");
        map.put("samurai", "侍");
        map.put("engineer", "エンジニア");
        return map;
    }

}
