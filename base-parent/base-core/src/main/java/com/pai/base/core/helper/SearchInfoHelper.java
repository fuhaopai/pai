package com.pai.base.core.helper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.pai.base.api.annotion.IField;
import com.pai.base.api.annotion.ITable;
import com.pai.base.api.annotion.PAIValidate;
import com.pai.base.api.model.FieldInfo;
import com.pai.base.api.model.TableInfo;
import com.pai.base.api.model.ValidateInfo;

public class SearchInfoHelper implements InitializingBean{

	private Resource[]  basePackage;
	
	private static Map<String, TableInfo> tableInfoMap = new HashMap<String, TableInfo>();
	private static Map<String, ValidateInfo> validateInfo = new HashMap<String, ValidateInfo>();
	
	public static TableInfo getTableInfo(String name){
		return tableInfoMap.get(name);
	}
	public static ValidateInfo getValidateInfo(String key){
		return validateInfo.get(key);
	}
		
	private void loadIFields(){
		try {
			List<Class<?>> tableList = TableScaner.findTableScan(basePackage);
		
			for (Class<?> cls : tableList) {
				ITable table = (ITable) cls.getAnnotation(ITable.class);
				if(table!=null){
					TableInfo tableInfo = new TableInfo(table);				
					Field[] fields = cls.getDeclaredFields();
					for (Field field : fields) {
						IField iField = field.getAnnotation(IField.class);
						if(iField!=null){
							FieldInfo fieldInfo = new FieldInfo(iField);
							tableInfo.addFieldInfo(fieldInfo);
						}
						
						PAIValidate validate = (PAIValidate) field.getAnnotation(PAIValidate.class);
						if(validate!=null){
							ValidateInfo valiadteInfo = new ValidateInfo(validate);				
							validateInfo.put(tableInfo.getName()+valiadteInfo.getKey(), valiadteInfo);
						}
					}
					tableInfoMap.put(tableInfo.getName(), tableInfo);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	
	
	public void afterPropertiesSet() throws Exception {
		loadIFields();
	}

	public void setBasePackage(Resource[] basePackage) {
		this.basePackage = basePackage;
	}
	
}
