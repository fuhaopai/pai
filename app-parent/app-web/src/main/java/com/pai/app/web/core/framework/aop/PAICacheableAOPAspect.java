package com.pai.app.web.core.framework.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.pai.app.web.core.framework.engine.FreemarkEngine;
import com.pai.base.api.annotion.PAICacheable;
import com.pai.base.core.util.ConfigHelper;
import com.pai.base.core.util.string.StringCollections;
import com.pai.base.core.util.string.StringConverter;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.redis.JedisUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class PAICacheableAOPAspect {
	private static Logger log = Logger.getLogger(PAICacheableAOPAspect.class);
	
	@Resource
	private FreemarkEngine freemarkEngine;
	
	//添加FreeMarker可访问的类静态方法的字段
	static Map<String,TemplateHashModel> STATIC_CLASSES = new HashMap<String, TemplateHashModel>();
	static{
		try {
			BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModel = beansWrapper.getStaticModels();
			STATIC_CLASSES.put(Long.class.getSimpleName(), (TemplateHashModel) staticModel.get(java.lang.Long.class.getName()));
			STATIC_CLASSES.put(Integer.class.getSimpleName(), (TemplateHashModel) staticModel.get(java.lang.Integer.class.getName()));
			STATIC_CLASSES.put(java.lang.String.class.getSimpleName(), (TemplateHashModel) staticModel.get(java.lang.String.class.getName()));
			STATIC_CLASSES.put(Short.class.getSimpleName(), (TemplateHashModel) staticModel.get(java.lang.Short.class.getName()));
			STATIC_CLASSES.put(Boolean.class.getSimpleName(), (TemplateHashModel) staticModel.get(java.lang.Boolean.class.getName()));
			STATIC_CLASSES.put(StringUtils.class.getSimpleName(),(TemplateHashModel) staticModel.get(StringUtils.class.getName()));
		} catch (TemplateModelException e) {
			e.printStackTrace();
		} 
	}	
	
	public Object doCacheable(ProceedingJoinPoint point) throws Throwable{
		Object returnVal=null;
		
		String methodName = point.getSignature().getName();
		//类
		Class<?> targetClass = point.getTarget().getClass();
		//方法
		Method[] methods = targetClass.getMethods();
		Method method = null;
		for (int i = 0; i < methods.length; i++){
			if (methods[i].getName() == methodName){
				method = methods[i];	 				
				break;
			}
		}
	
		//如果横切点不是方法，返回
		if (method == null)
			return point.proceed();
				
		PAICacheable paiCacheable = method.getAnnotation(PAICacheable.class);
		
		if(paiCacheable!=null){
			int seconds = paiCacheable.seconds();
			try {
				String defaultSecondPriority = ConfigHelper.getInstance().getParamValue("cache.defaultSecondPriority");
				if("Y".equals(defaultSecondPriority)){
					String strDefaultSeconds = ConfigHelper.getInstance().getParamValue("cache.defaultSeconds");
					Integer defaultSeconds = StringConverter.toInteger(strDefaultSeconds);
					if(defaultSeconds!=null && defaultSeconds>0){
						seconds = defaultSeconds;
					}
				}	
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			List<String> paramNames = StringCollections.toList(paiCacheable.params(), ",");
			Map<String, Object> map=new HashMap<String, Object>();
			map.putAll(STATIC_CLASSES);		
			if(point.getArgs()!=null && point.getArgs().length>0 && paramNames!=null && paramNames.size()>0){
				int i = 0;
				for(String paramName:paramNames){					
					map.put(StringUtils.lowerFirst(paramName), point.getArgs()[i]);
					i++;
				}
			}
			try {
				String key="";
				String annotationKey=paiCacheable.key();
				if(annotationKey!=null&&!annotationKey.equals("")){
					key=targetClass.getName() + "_" + freemarkEngine.parseByStringTemplate(annotationKey, map);					
				}
				  
				Object objcet=JedisUtil.getInstance().getObject(key,paiCacheable.db());
				if(isEmpty(objcet)){
					returnVal= point.proceed();
					
					if(returnVal!=null){
						if(returnVal instanceof Serializable){
							JedisUtil.getInstance().set(key, returnVal, paiCacheable.db());
//							JedisUtil.getInstance().set(key,JsonUtil.getJSONString(returnVal),paiCacheable.db());
							JedisUtil.getInstance().expire(key, seconds);
						}else {
							log.warn(returnVal.getClass().getName() + " must implements Serializable!!");
						}
					}
					
					return  returnVal;
				}else {
					return objcet;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}		
		}else {
			//如果方法上没有注解@PAICacheable，返回
			return point.proceed();
		}
		
		return point.proceed();
	}
	
	private boolean isEmpty(Object object){
		if(object==null){
			return true;
		}else{
			String str = object.toString();
			if(str.equals("[]") || str.equals("{}")){
				return true;
			}			
		}
		return false;
	}
}
