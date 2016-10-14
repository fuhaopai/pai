package com.pai.base.api.model;

import com.pai.base.api.annotion.IField;


public class FieldInfo {
	private String name;
	private String column;
	public FieldInfo(IField field){
		name=field.name();
		column=field.column();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	
}
