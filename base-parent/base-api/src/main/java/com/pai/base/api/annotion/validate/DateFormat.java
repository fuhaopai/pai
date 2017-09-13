package com.pai.base.api.annotion.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要符合SimpleDateFormat
 * @ClassName: DateFormat 
 * @Description: TODO
 * @author: fuhao
 * @date: 2017年6月20日 下午5:41:12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
	public String fieldName();
	public String format();
}
