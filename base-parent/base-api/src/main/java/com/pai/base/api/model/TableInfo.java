package com.pai.base.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pai.base.api.annotion.ITable;

public class TableInfo {
	private String name;
	private String code;
	private List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();
	private Map<String,FieldInfo> fieldInfosMap = new HashMap<String,FieldInfo>();

	public TableInfo(ITable table){
		name=table.name();
		code = table.code();		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void addFieldInfo(FieldInfo fieldInfo) {
		fieldInfos.add(fieldInfo);
		fieldInfosMap.put(fieldInfo.getName().toLowerCase(), fieldInfo);
	}
	public FieldInfo getFieldInfo(String name){
		if(name!=null){
			return fieldInfosMap.get(name.toLowerCase());
		}
		return null;
	}
	
}
