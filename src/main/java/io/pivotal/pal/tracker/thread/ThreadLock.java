package io.pivotal.pal.tracker.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tx.util.StopWatch;

@RestController
public class ThreadLock {
    private static Logger logger = LoggerFactory.getLogger(ThreadLock.class);
    static int    lockCnt = 0 ;

//    public ThreadLock(){}
   
    public synchronized static void lockAdd() {
        lockCnt++ ;
    }
    public synchronized void lockDelete() {
        lockCnt-- ;
        notify();
    }
    public synchronized void lockWait() {
        while ( lockCnt > 0 ) {
            try {
                logger.debug("suspend, lockCnt not 0 [{}]", lockCnt);
                wait();
            } catch (InterruptedException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        logger.debug("resume, lockCnt [{}]", lockCnt);
    }
}
