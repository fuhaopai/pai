package com.pai.app.web.core.framework.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;

import com.pai.app.web.core.framework.engine.FreemarkEngine;
import com.pai.base.api.annotion.SKGCacheEvict;
import com.pai.base.core.util.string.StringCollections;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.redis.JedisUtil;
import com.pai.service.redis.RedisDb;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class PAICacheEvictAOPAspect {
	
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
	
	public Object doCacheEvict(ProceedingJoinPoint point) throws Throwable{
		
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

		SKGCacheEvict skgCacheEvict = method.getAnnotation(SKGCacheEvict.class);
		 
		if(skgCacheEvict!=null){
			
			List<String> paramNames = StringCollections.toList(skgCacheEvict.params(), ",");
			
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
				String type=skgCacheEvict.type();
				int db=skgCacheEvict.db();
				if(type.equals(RedisDb.EVICT_ALL_TYPE)){//清空当前数据库
					JedisUtil.getInstance().flushDB(db);
				}else if(type.equals(RedisDb.EVICT_KEY_TYPE)) {//根据key删除缓存
					if(skgCacheEvict.key()!=null&&!skgCacheEvict.key().equals("")){
						String key=freemarkEngine.parseByStringTemplate(skgCacheEvict.key(), map);
						JedisUtil.getInstance().delByKey(key,db);
					}
					
				}else if(type.equals(RedisDb.EVICT_PREFIX_TYPE)){//根据前缀匹配删除
					String key = skgCacheEvict.key();
					JedisUtil.getInstance().delByPrefix(key,db);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}		
		}
		
		return point.proceed();
	}
	
	
}
