package com.pai.inf.validate.parser;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.pai.inf.validate.annotation.DateFormat;

/**
 * 日期格式注解解析器
 * 
 * @author fuhao
 *
 */
public class DateFormatParser implements IAnnotationParser{
	
	/**
	 * 校验f字段的值是否符合value的日期格式
	 * @see DateFormat
	 */
	@Override
	public ValidateResult validate(Field f, Object value) {
		ValidateResult result = new ValidateResult();
		if(f.isAnnotationPresent(DateFormat.class)){
			DateFormat dateFormat = f.getAnnotation(DateFormat.class);
			try {
				if(value != null){
					SimpleDateFormat format = new SimpleDateFormat(dateFormat.format());
					format.parse(value.toString());
				}
			} catch (ParseException e) {
				result.setMessage(dateFormat.fieldName() + "不满足格式：" + dateFormat.format());
			}	
		}
		return result;
	}
}
