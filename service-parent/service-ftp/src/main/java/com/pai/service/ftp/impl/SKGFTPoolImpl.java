package com.pai.service.ftp.impl;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.pai.service.ftp.SKGFTPool;
import com.pai.service.ftp.common.SKGFTPClient;

/**
 *   SKGFTP连接池
 *   @author Suoron
 *   @since 2015-10-22  
 * 
 */

public class SKGFTPoolImpl implements SKGFTPool<SKGFTPClient>{
	
	   private GenericObjectPool<SKGFTPClient> ftPool = null;  
       	    
	    public SKGFTPoolImpl(GenericObjectPool.Config poolConfig, PoolableObjectFactory factory)    
	    {  
	    	  this.ftPool = new GenericObjectPool<SKGFTPClient>(factory, poolConfig);   
	    }  
	      
	    public SKGFTPClient getResource(){  
	        try{  	        	
	            return this.ftPool.borrowObject();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	      
	    public void returnResource(SKGFTPClient resource){  
	        try {  
	            this.ftPool.returnObject(resource);  
	        }catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	      
	    public void destroy(){  
	        try{  
	            this.ftPool.close();    
	        }catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	      
	    public int inPoolSize(){  
	        try{  
	            return this.ftPool.getNumIdle();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	            return 0;  
	        }  
	    }  
	      
	    public int borrowSize(){  
	        try{  
	            return this.ftPool.getNumActive();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	            return 0;  
	        }  
	    }       
}
