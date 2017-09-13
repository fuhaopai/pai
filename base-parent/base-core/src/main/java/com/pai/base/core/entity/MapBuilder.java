package com.pai.base.core.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre> 
 * 描述：使用例子：buildMapBuilder().addParam("procDefId",procDefId).addParam("nodeId",nodeId).getParams()
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2016-09-29 18:00:11
 * </pre>
 */
public class MapBuilder{
	Map<String, Object> map = new HashMap<String, Object>();
	public MapBuilder(){    		
	}
	
	private static class SingletonHolder {
		public final static MapBuilder instance = new MapBuilder();
	}

	public static MapBuilder getInstance() {
		return SingletonHolder.instance;
	}
	
	public MapBuilder addParam(String paramKey,Object paramValue){
		map.put(paramKey, paramValue);
		return this;
	}
	/**
	 * 和addParam同义，缩写。
	 * @param paramKey
	 * @param paramValue
	 * @return 
	 * MapBuilder
	 * @exception 
	 * @since  1.0.0
	 */
	public MapBuilder a(String paramKey,Object paramValue){
		map.put(paramKey, paramValue);
		return this;
	}
	public Map<String, Object> getParams(){
		return map;
	}    	
	/**
	 * 和getParams同义，缩写。
	 * @return 
	 * Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	public Map<String, Object> p(){
		return map;
	}
}
