package com.pai.app.web.core.framework.filter;

import java.lang.reflect.Method;
import java.util.Properties;

import com.pai.base.core.util.string.StringUtils;

class RunHelper {
	@SuppressWarnings("rawtypes")
	private Class helperCls;
	private Object helperInstance;
	private boolean jarExists = false;
	
	public static RunHelper runHelper = null;
	
	private RunHelper(){		
	}
	
	public static RunHelper getInstance(){
		if(runHelper==null){
			runHelper = new RunHelper();
		}
		runHelper.init();
		return runHelper;
	}
	
	
	private void init(){
		try {
			if(helperCls==null)
				helperCls = Class.forName("com.skg.base.core.DeEnHelper");
			if(helperInstance==null)
				helperInstance = helperCls.newInstance();
		} catch (Exception e) {
			//e.printStackTrace();
		}		
		if(helperCls!=null){
			jarExists = true;
		}
	} 	
	
	public String[] loadProperties() {
		String[] args =new String[2];
		Properties properties = new Properties();
		try {
			properties.load(RunFilter.class.getClassLoader()
					.getResourceAsStream("conf/app-web.properties"));
			args[0] = properties.getProperty("check.key");
			args[1] = properties.getProperty("check.keyEn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return args;
	}

	public boolean check(String key, String keyEn) {
		if(StringUtils.isEmpty(key) || StringUtils.isEmpty(keyEn)){
			return false;
		}
		try {
			@SuppressWarnings("unchecked")
			Method checkMethod = helperCls.getDeclaredMethod("check", String.class,
					String.class);
			boolean flag = (Boolean) checkMethod.invoke(helperInstance, key, keyEn);
			return flag;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return true;
	}	
	
	public boolean isPass(Integer counter){
		try {
			Method isPassMethod = helperCls.getDeclaredMethod("isPass", Integer.class);
			boolean flag = (Boolean) isPassMethod.invoke(helperInstance, counter);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	public boolean isExists(){
		return jarExists;
	}
	
	public boolean isDefaultCheckPass(Integer counter){
		if(counter<100000){
			return true;
		}
		return false;
	}
}
