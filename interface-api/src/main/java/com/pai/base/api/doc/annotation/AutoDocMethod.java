package com.pai.base.api.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pai.base.api.doc.constants.DeveloperType;
import com.pai.base.api.doc.constants.ModuleType;
import com.pai.base.api.doc.constants.VersionType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoDocMethod {

	public ModuleType module();

	public String name();

	public String description() default "";

	public VersionType cver();

	public VersionType uver() default VersionType.V_DEFAULT;

	public DeveloperType author();

	public String createTime();

	public String updateTime() default "";

	public DeveloperType updateBy() default DeveloperType.DEFAULT;

	public String[] modify() default "";

	public boolean deprecated() default false;
	
	public int sort() default 100;

}
