package com.pai.service.redis.util;

import org.apache.log4j.Logger;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

public abstract class RedisExecutor<T> {
	int errorCount = 0; 
	protected static final Logger log =  Logger.getLogger(RedisExecutor.class); 
	static Boolean isPass = false; 
	public Transaction tran = null;
    public ShardedJedis shardedJedis = null;  
    public ShardedJedisPool shardedJedisPool = null;  	
	
	//如果出现异常，最长等待5秒
	private static CrachLatch mylatch = new CrachLatch(5000L);
	
    public RedisExecutor(ShardedJedisPool pool){ 
   	     this.shardedJedisPool = pool; 
   	     shardedJedis = this.shardedJedisPool.getResource();
    } 
    
	public T exec() throws RuntimeException{ 
	       boolean isCatch = false; 
	       try{ 
	   		     mylatch.waitLatch(); 	   		      
	   		     return run(); 
	   		}catch(Exception e){ 
	   		    isCatch = true; 	   		   
	   		    if(e instanceof JedisConnectionException){ 
	   		        errorCount++; 
	   		        log.info("new jedis pool :"+isPass); 
	   		        isPass = false; 
	   		        synchronized (mylatch) { 
	   		        if(!isPass){ 
	   		           try { 	   		 
	   		              mylatch.wakeupWait();   ////唤醒锁让所有redis的执行的等待 
	   		              //RedisManager.getInstance().destoryPool(); 
	   		              isPass = true; 
	   		              mylatch.countDown();//开锁,所有redis操作继续 
	   		              log.info("open lock~~~"); 
	   		           } catch (InterruptedException e1) { 
	   		                 throw new RuntimeException(e); 
	   		         } 
	   		      } 
	   		      log.info("execute again!!!!!!!!!!"); 
	   		} 
	   		//jedisPool = RedisManager.getInstance().getJedisPool(); 
	   		if(errorCount<5){ 
  	   		     return exec();//在新的jedispool下再跑一遍 
	   		}else{ 
	   		    /// throw new PiRedisExecption(e); 
	   		} 

	   		} 
	   		} finally {  
	   			if(tran!=null){ 
	   		   		tran.discard(); 
	   		   	} 	   			
	            if (shardedJedis != null) {
	                shardedJedisPool.returnResource(shardedJedis);  
	            }  
	        }
	       return null;
	} 
	protected abstract T run() throws Exception; 

} 
