package com.pai.base.api.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited
public @interface PAICacheEvict {
	/**
	 * 缓存key
	 * @return
	 */
	public String key() default "";
	/**
	 * 缓存数据库
	 * @return
	 */
	public int db();
	/**
	 *类型:<br>
	 *       key=根据key删除缓存<br>
	 *       all=清除当前db所有缓存<br>
	 *       prefix=根据前缀删除缓存<br>
	 * @return
	 */
	public String type();
	
	public String params() default "" ;
}