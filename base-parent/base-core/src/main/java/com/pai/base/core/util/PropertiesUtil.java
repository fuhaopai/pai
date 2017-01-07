package com.pai.base.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * <pre> 
 * 描述：配置对象工具类
 * 构建组：base-core
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2016-09-29 18:00:11
 * </pre>
 */
public class PropertiesUtil {
	
	/**
	 * 根据路径读取配置对象
	 * @param path
	 * @return 
	 * Properties
	 * @exception 
	 * @since  1.0.0
	 */
	public static Properties loadProperties(String path){
		Properties properties = null;
		InputStream is = null;
		try {
			properties = new Properties();
			is = new FileInputStream(path);
			properties.load(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
		}	
		return properties;
	}
	
	/**
	 * 将源配置对象的值集合合并到目标配置对象中
	 * @param srcProperties
	 * @param destProperties 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void mergeTo(Properties srcProperties,Properties destProperties){
		for(Iterator<Map.Entry<Object,Object>> iterator = srcProperties.entrySet().iterator();iterator.hasNext();){
			Map.Entry<Object, Object> entry = iterator.next();
			destProperties.put(entry.getKey(), entry.getValue());
		}
	}
}
