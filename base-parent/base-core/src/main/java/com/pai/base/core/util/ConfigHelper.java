package com.pai.base.core.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.pai.base.api.helper.IConfigHelper;
import com.pai.base.core.helper.SpringHelper;
import com.pai.base.core.util.FileUtils;
import com.pai.base.core.util.PropertiesUtil;
import com.pai.base.core.util.string.StringCollections;
import com.pai.base.core.util.string.StringUtils;

public class ConfigHelper implements IConfigHelper{
	//private Properties properties = new Properties();
	private Properties properties = null;   //modify by suoron on 2015-08-12

	public static ConfigHelper getInstance() {
		ConfigHelper configHelper = SpringHelper.getBean(ConfigHelper.class);
		return configHelper;
	}
	
	public void init(){
		properties = new Properties();
		String path = ServletContextHelper.getRealPath() + "/WEB-INF/classes/conf";
		List<String> filePaths = FileUtils.getFilePaths(path, "properties", true);
		if(filePaths.isEmpty()){
			path = ServletContextHelper.getRealPath() + "/conf";
			filePaths = FileUtils.getFilePaths(path, "properties", true);
		}
		if(filePaths.size()>0){
			int len = filePaths.size();
			for (int i = 0; i < len; i++) {
				if(i==0){
					properties = PropertiesUtil.loadProperties(filePaths.get(i));		
				}else{
					Properties temp = PropertiesUtil.loadProperties(filePaths.get(i));
					PropertiesUtil.mergeTo(temp, properties);
				}
			}		
		}else {
	    	try {
	    		properties.load(ConfigHelper.class.getClassLoader().getResourceAsStream("conf/app-web.properties"));
	    		Properties baseDbProperties = new Properties();
	    		baseDbProperties.load(ConfigHelper.class.getClassLoader().getResourceAsStream("conf/base-db.properties"));
	    		PropertiesUtil.mergeTo(baseDbProperties,properties);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getParamValue(String paramName){
		if(properties==null){
			init();
		}
		if(properties!=null){
			return properties.getProperty(paramName);
		}
		return "";
	}
	
	/**
	 *    查找以likeName开头的所以配置项
	 *    @likeName 配置项前缀，比如:mq.to_xxx
	 *    
	 */
	public List<String> getLikeToList(String likeName){
		List<String> list = new ArrayList<String>();
		if(properties != null){
			Enumeration enu=properties.propertyNames();
			while(enu.hasMoreElements()){
			    String key = (String)enu.nextElement();
			    if(key.indexOf(likeName) == 0)
			    	list.add(getParamValue(key));
			}
		}
		return list;
	}	
	public List<String> getParamToList(String paramName){
		String paramValue = getParamValue(paramName);
		if(StringUtils.isNotEmpty(paramValue)){
			return StringCollections.toList(paramValue, ",");
		}
		return new ArrayList<String>();
	}

	public List<Integer> getParamToListInteger(String paramName) {
		String paramValue = getParamValue(paramName);
		if(StringUtils.isNotEmpty(paramValue)){
			return StringCollections.toListInteger(paramValue, ",");
		}
		return new ArrayList<Integer>();
	}

	public boolean getBool(String paramName) {
		String paramValue = getParamValue(paramName);
		if(StringUtils.isNotEmpty(paramValue)){
			if(paramValue.equalsIgnoreCase("Y") 
					|| paramValue.equalsIgnoreCase("YES")
					|| paramValue.equalsIgnoreCase("TRUE")
					|| paramValue.equalsIgnoreCase("1")){
				return true;
			}
		}
		return false;
	}

	public int getInt(String paramName) {
		String paramValue = getParamValue(paramName);
		if(StringUtils.isNotEmpty(paramValue)){
			try{
				return Integer.valueOf(paramValue);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	
}
