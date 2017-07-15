package com.pai.app.web.core.framework.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pai.app.web.core.framework.constants.AopExecOrder;

/**
 * 类的方法描述注解,用于aop拦截以获取正确方法其对应的描述
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited 
public @interface Audit {
	String  name() default ""; 		/*操作名称*/
	//String  execTime() default  ""; 		/*执行时间*/
	//String  execMethod() default ""; 		/*执行方法。格式为  类名.方法名*/
	//String  reqUri () default ""; 		/*请求URL*/
	//String  reqParams() default ""; 		/*请求参数*/
	//String  module() default "" ; 		/*归属模块*/
	//String  type() default "" ; 		/*日志类型*/
	//String  executorId() default "" ; 		/*执行人ID*/
	//String  executorName() default "" ; 		/*执行人姓名*/
	//String  executorAccount() default "" ; 		/*执行人用户账号*/
	//String  executorIp() default "" ; 		/*执行人的IP*/
	//String  detail() default "" ; 		/*明细信息*/
	
	/**
	 * 执行顺序
	 */
	public AopExecOrder execOrder() default AopExecOrder.AFTER;
}
