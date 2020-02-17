package io.pivotal.pal.tracker.thread;

import io.pivotal.pal.tracker.Invoice;
import io.pivotal.pal.tracker.InvoiceClient;
import io.pivotal.pal.tracker.WelcomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tx.util.StopWatch;

public class InvoiceRepoMsaChildThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
//	static int    lockCnt = 0 ;
	
//	WelcomeController lock = null ;
	ThreadLock lock = new ThreadLock();
	long cnt;
	Integer number;
	InvoiceClient invoiceClient;
	
	public InvoiceRepoMsaChildThread(ThreadLock lock, long cnt, Integer number, InvoiceClient invoiceClient) {
		super();
		this.lock = lock;
		this.cnt = cnt;
		this.number = number;
		this.invoiceClient = invoiceClient;
//		WelcomeController.lockAdd();
		ThreadLock.lockAdd();
	}
	
	@Override
	public void run(){
		logger.debug("child thread number [{}]", cnt); ;
		StopWatch sw = new StopWatch();
		sw.start();
		
		//bujiness logic
		Integer searchNumber = 0;
		for (Integer i=1; i<=number; i++ ){
			searchNumber++;
			if (searchNumber == 501){searchNumber = 1;}
			Invoice invoice = invoiceClient.find(searchNumber);
			if (invoice != null) {
				logger.debug("InvoiceNumber[{}], HttpStatus[{}]", invoice.getInvoiceNumber(), HttpStatus.OK);
			} else 	{
				searchNumber = 0;
			}
		}
		sw.stopReport("<<< thread["+cnt+"], number["+number+"], invoice micro service excute time >>>");
//		Invoice invoice = invoiceClient.find(1);
//		if (invoice != null) {
//			logger.debug("InvoiceNumber[{}], HttpStatus[{}]", invoice.getInvoiceNumber(), HttpStatus.OK);
////			return new ResponseEntity<>(invoice, HttpStatus.OK);
//		} else {
//			logger.debug("InvoiceNumber[null], HttpStatus[{}]", HttpStatus.OK);
////			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
		
		lock.lockDelete();
	}
}
