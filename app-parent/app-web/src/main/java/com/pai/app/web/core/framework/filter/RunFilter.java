package com.pai.app.web.core.framework.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public abstract class RunFilter extends OncePerRequestFilter{
	//private final static int runCheck = 
	
	protected void doFilterRun(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {	
//		RunHelper runHelper = RunHelper.getInstance();
//		String[] args = runHelper.loadProperties();
//		
//		counter++;
//		if(runHelper.isExists()){			
//			boolean checkPass = runHelper.check(args[0],args[1]); 
//			if(!checkPass){				
//				if(!runHelper.isPass(counter)){					
//					throw new RuntimeException(RunFilter.class + ": counter overflow!");
//				}
//			}
//		}else {
//			if(!runHelper.isDefaultCheckPass(counter)){
//				throw new RuntimeException(RunFilter.class + ": counter overflow (default) !");
//			}
//		}	
	}

    private static int counter = 0;    

}
