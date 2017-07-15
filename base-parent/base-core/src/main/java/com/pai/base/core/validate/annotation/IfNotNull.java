package com.pai.base.core.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 条件判断，如果条件成立后进行非空校验
 * @ClassName: If 
 * @Description: TODO
 * @author: fuhao
 * @date: 2017年6月20日 下午5:39:39
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IfNotNull {
	public String condition();
	public NotNull field();
}
