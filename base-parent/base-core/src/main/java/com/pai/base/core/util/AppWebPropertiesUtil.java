package com.pai.base.core.util;

import java.util.Properties;

public class AppWebPropertiesUtil {
	private static Properties properties = new Properties();
	private static boolean isLoad;
    public static String getProperties(String propKey){    	
    	try {
    		if(!isLoad){
    			properties.load(AppWebPropertiesUtil.class.getClassLoader().getResourceAsStream("conf/app-web.properties"));
    			isLoad = true;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	String value = properties.getProperty(propKey);
    	return value;
    }
}
