
package com.pai.base.core.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeneralException extends RuntimeException {
	private Log logger = null;
	
	private GeneralException() {
	}
	
	public GeneralException(Class clazz,String message) {		
		super(message);
		logger = LogFactory.getLog(clazz);
		logger.error(message);
	}
	
	public GeneralException(Class clazz,String message, Throwable cause) {
		super(message, cause);
		logger = LogFactory.getLog(clazz);
		logger.error(message);		
	}
	
	public GeneralException(Class clazz,Throwable cause) {
		super(cause);
		logger = LogFactory.getLog(clazz);
		logger.error(cause.getMessage());	
	}

	private static final long serialVersionUID = 2174655670701233591L;
}
