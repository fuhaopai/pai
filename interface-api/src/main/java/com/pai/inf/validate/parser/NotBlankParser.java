package com.pai.inf.validate.parser;

import java.lang.reflect.Field;

import com.pai.inf.validate.annotation.NotBlank;

/**
 * 不能为空白校验器
 * @author fuhao
 *
 */
public class NotBlankParser implements IAnnotationParser {
	
	/**
	 * 校验字段f的值不能为null或者是空字符串，校验结果保存在result中
	 */
	@Override
	public ValidateResult validate(Field f, Object value) {
		ValidateResult result = new ValidateResult();
		if(f.isAnnotationPresent(NotBlank.class)){
			NotBlank notBlank = f.getAnnotation(NotBlank.class);
			if(value == null || value.toString().length() == 0){
				result.setMessage(notBlank.fieldName() + "不能为空");
			}
		}
		return result;
	}

}
