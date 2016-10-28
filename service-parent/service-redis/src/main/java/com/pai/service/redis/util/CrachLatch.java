package com.pai.service.redis.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CrachLatch {
	Long timeout = 1L; 
	
	public CrachLatch(Long timeout){ 
	    this.timeout = timeout; 
	} 
	
	public CountDownLatch latch = null; 

	public void waitLatch() throws InterruptedException{		
		if(latch!=null){ 
			latch.await(this.timeout*2,TimeUnit.MILLISECONDS); 
		} 
	} 
	
	public void wakeupWait() throws InterruptedException{ 
		latch = new CountDownLatch(1); 
		Thread.sleep(this.timeout); 
	} 
	public void countDown(){ 
	    latch.countDown(); 
	    latch = null; 
	} 
}
