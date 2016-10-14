package com.pai.base.core.util.date;

import org.apache.commons.beanutils.Converter;

public class BeanUtilDateConverter implements Converter{

	 public Object convert(Class arg0, Object arg1) {
	        String p = (String)arg1;
	        if(p== null || p.trim().length()==0){
	        	return null;
	        }   
	        try {
		        return DateConverter.toDate(p.trim());	
			} catch (Exception e) {
				return null;
			}
	 }
}
