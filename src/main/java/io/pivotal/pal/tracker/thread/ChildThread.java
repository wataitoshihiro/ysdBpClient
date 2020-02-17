package io.pivotal.pal.tracker.thread;

import io.pivotal.pal.tracker.WelcomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChildThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
//	static int    lockCnt = 0 ;
	
//	WelcomeController lock = null ;
	ThreadLock lock = new ThreadLock();
	long cnt;
	
	public ChildThread(ThreadLock lock, long cnt) {
		super();
		this.lock = lock;
		this.cnt = cnt;
//		WelcomeController.lockAdd();
		ThreadLock.lockAdd();
	}
	
	@Override
	public void run(){
		logger.debug("thread num [{}]", cnt) ;
//		ThreadLock.lockAdd();
		lock.lockDelete();
	}
}
