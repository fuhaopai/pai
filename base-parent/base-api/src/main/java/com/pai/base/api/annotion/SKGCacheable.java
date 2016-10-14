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
public @interface SKGCacheable {
	public String key();
	public int db();
	public String params() default "" ;
	public int seconds() default 60*60*24;
}