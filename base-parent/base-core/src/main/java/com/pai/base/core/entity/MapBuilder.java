package com.pai.base.core.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre> 
 * 描述：使用例子：buildMapBuilder().addParam("procDefId",procDefId).addParam("nodeId",nodeId).getParams()
 * 构建组：x5-base-db
 * 作者：winston yan
 * 邮箱:yancm@jee-soft.cn
 * 日期:2014-1-18-上午10:47:36
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MapBuilder{
	Map<String, Object> map = new HashMap<String, Object>();
	public MapBuilder(){    		
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
