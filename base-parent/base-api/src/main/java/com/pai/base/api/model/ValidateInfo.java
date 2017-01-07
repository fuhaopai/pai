package com.pai.base.api.model;

import com.pai.base.api.annotion.PAIValidate;


public class ValidateInfo {
	public ValidateInfo(PAIValidate paiValidate){
		key = paiValidate.key();
	}
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
