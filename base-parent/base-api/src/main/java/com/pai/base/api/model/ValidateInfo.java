package com.pai.base.api.model;

import com.pai.base.api.annotion.SKGValidate;


public class ValidateInfo {
	public ValidateInfo(SKGValidate skgValidate){
		key = skgValidate.key();
	}
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
