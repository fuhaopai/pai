package com.pai.base.api.validate;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pai.base.api.validate.parser.DateFormatParser;
import com.pai.base.api.validate.parser.IAnnotationParser;
import com.pai.base.api.validate.parser.NotBlankParser;
import com.pai.base.api.validate.parser.NotNullParser;
import com.pai.base.api.validate.parser.ValidateResult;

/**
 * 注解校验器
 * @ClassName: AnnotationValidator 
 * @Description: TODO
 * @author: fuhao
 * @date: 2017年6月20日 下午5:41:56
 */
public class AnnotationValidator {
	private static final Logger log = Logger.getLogger(AnnotationValidator.class.getName());
	
	private final static List<IAnnotationParser> vList = new ArrayList<IAnnotationParser>();
	static {
		vList.add(new NotNullParser());
		vList.add(new NotBlankParser());
		vList.add(new DateFormatParser());
	}
	
	/**
	 * 遍历所有字段，用所有解析器进行校验，如果校验失败，则终止校验返回结果，如果校验成功，同样返回校验结果
	 * @param t
	 * @return
	 */
	public static <T> ValidateResult validate(T t){
		ValidateResult result = null;
		for (Field f : t.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			Object value = null;
			try {
				value = f.get(t);
			} catch (IllegalArgumentException e) {
				log.log(Level.SEVERE, "Exception", e);
			} catch (IllegalAccessException e) {
				log.log(Level.SEVERE, "Exception", e);
			}
			
			for (IAnnotationParser va : vList) {
				result = va.validate(f, value);
				if(!result.isValid()){
					return result;
				}
			}
		}
		return result;
	}
	
	/**
	 * 注册解析器
	 * @param parser
	 */
	public static void register(IAnnotationParser parser){
		vList.add(parser);
	}
}
