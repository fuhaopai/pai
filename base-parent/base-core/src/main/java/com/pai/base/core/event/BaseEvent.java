package com.pai.base.core.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class BaseEvent extends ApplicationEvent{
	public final static String DEFAULT_SOURCE = "default_source";
	public final static String CLASS_NAME = "class_name";
	public final static String SIMPLE_NAME = "simple_name";
	private Map<String, Object> dataMap;
	public BaseEvent(Object source,Map<String, Object> dataMap) {		
		super(source);		
		this.dataMap = dataMap;
		if(this.dataMap==null){
			this.dataMap = b();
		}
		this.dataMap.put(DEFAULT_SOURCE, source);
		this.dataMap.put(CLASS_NAME, this.getClass().getName());
		this.dataMap.put(SIMPLE_NAME, this.getClass().getSimpleName());
	}
	protected static Map<String, Object> b() {
		return new HashMap<String,Object>();
	}	
}
