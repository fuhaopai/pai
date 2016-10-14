package com.pai.base.api.template.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TemplateVarsContainer {
	private Map<String,Object> templateVars = new HashMap<String, Object>();

	public Map<String, Object> getTemplateVars() {
		return Collections.unmodifiableMap(templateVars);
	}
	
	public void put(String key,Object value){
		templateVars.put(key, value);
	}
	
}
