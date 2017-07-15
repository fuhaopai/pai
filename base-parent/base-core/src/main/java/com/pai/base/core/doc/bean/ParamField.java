package com.pai.base.core.doc.bean;

public class ParamField {
	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private String type;
	/**
	 * 字段注释
	 */
	private String note;
	
	/**
	 * 是否必填
	 */
	private boolean required;

	public ParamField() {
	}

	public ParamField(String name, String type) {
		this(name, type, "");
	}

	public ParamField(String name, String type, String note) {
		this.name = name;
		this.type = type;
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
	
}
