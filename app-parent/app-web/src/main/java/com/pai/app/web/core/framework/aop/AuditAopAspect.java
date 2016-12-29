package com.pai.app.web.core.framework.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;

import com.pai.app.web.core.framework.annotion.Audit;
import com.pai.app.web.core.framework.constants.AopExecOrder;
import com.pai.app.web.core.framework.engine.FreemarkEngine;
import com.pai.app.web.core.framework.web.context.WebOnlineHolder;
import com.pai.base.core.util.string.StringUtils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class AuditAopAspect {
	
	/*@Resource 
	private GlAuditRepository glAuditRepository;*/
	
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
	
	public Object doAudit(ProceedingJoinPoint point) throws Throwable{
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
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.putAll(STATIC_CLASSES);
		for(Object obj:point.getArgs()){
			if(obj!=null && obj.getClass()!=null)
				map.put(StringUtils.lowerFirst(obj.getClass().getSimpleName()), obj);
		}
		
		//如果横切点不是方法，返回
		if (method == null)
			return point.proceed();
		
		//boolean hasAnnotation = method.isAnnotationPresent(Action.class);
		//方法Action
		Audit annotation = method.getAnnotation(Audit.class);
		//如果方法上没有注解@Action，返回
		if(annotation==null){
			return point.proceed();
		}
		

		if(AopExecOrder.BEFORE.equals(annotation.execOrder())){
			doAuditLog(annotation,point,false);
			returnVal = point.proceed();
		}else if(AopExecOrder.AFTER.equals(annotation.execOrder())){
			returnVal = point.proceed();
			doAuditLog(annotation,point,true);
		}else{
			returnVal = point.proceed();
			doAuditLog(annotation,point,true);
		}
		return returnVal;
	}
	
	@SuppressWarnings("unchecked")
	private void doAuditLog(Audit annotation,ProceedingJoinPoint point,boolean async){
		//TODO 记录日志
		HttpServletRequest request=null;
		Map map=new HashMap();
		map.put("listData","查询");
		map.put("edit","编辑");
		map.put("save","保存");
		map.put("delete","删除");
		 Object[] args = point.getArgs();
		 // 获取参数
		 for (Object obj : args) 
			 if(obj instanceof  ServletRequest )
				 request=(HttpServletRequest) obj;
		 
		 if(request==null)
			 return;
		 
		 //获取用户
		/* UserPo userPo = OuOnlineHolder.getUserPo();
		 if(userPo==null)
			 return;*/

		 //遍历请求参数
	 StringBuffer  ReqParams =new StringBuffer();
		 Map parameterMap = request.getParameterMap();
		 Set<Map.Entry<String, String>> entrySet = parameterMap.entrySet();
		 for (Map.Entry<String, String> entry : entrySet) {
			 String key = entry.getKey();
			 ReqParams.append(key);
			 ReqParams.append(":");
			 ReqParams.append(request.getParameter(key));
			 ReqParams.append(",");
		}
	 
		/*GlAuditPo glAuditPo=new GlAuditPo();
		操作名称
		glAuditPo.setName(annotation.name());
		执行时间
		glAuditPo.setExecTime(new Date());
		
		String claName = point.getTarget().getClass().getName();
		String methodName = claName+"."+point.getSignature().getName();
		//System.out.println("方法名："+methodName);
		glAuditPo.setExecMethod(methodName);
		
		glAuditPo.setReqUri(request.getRequestURL().toString());
		//System.out.println("请求URL"+request.getRequestURL().toString());
		
		glAuditPo.setReqParams( ReqParams.toString() );
	   // System.out.println("请求参数"+ReqParams.toString());
		
	    String requestURI =request.getRequestURI();
		String substring = requestURI.substring(1, requestURI.lastIndexOf("/")) ;
		substring=substring.replace('/','.');
		glAuditPo.setModule(substring);
		//System.out.println("归属模块:"+substring);
		
		glAuditPo.setType((String)map.get(point.getSignature().getName()));
		//System.out.println("日志类型"+point.getSignature().getName());
		
		glAuditPo.setExecutorId(userPo.getId());
		//System.out.println("执行人ID"+userPo.getId());
		
		glAuditPo.setExecutorName(userPo.getName());
		//System.out.println("执行人姓名"+userPo.getName());
		
		glAuditPo.setExecutorAccount(userPo.getAccount());
		//System.out.println("执行人的账号"+userPo.getAccount());
	    
		glAuditPo.setExecutorIp(request.getRemoteAddr());
		//System.out.println("ip:"+request.getRemoteAddr());
		
		StringBuffer detail=new StringBuffer();
		detail.append("{");
			detail.append("executeObj:");
			detail.append("{ Name:");
			//获取被操作类
			claName=claName.substring(claName.lastIndexOf(".")+1, claName.length());
			detail.append(claName);
			detail.append("},");
			detail.append("operateOPObj:");
			detail.append("{ Name:");
			detail.append(annotation.name());
			detail.append("}");
		detail.append("}");
		//System.out.println("明细信息："+detail.toString());
		glAuditPo.setDetail(detail.toString());
		if(async){
			//后置操作
			GlAudit glAudit = glAuditRepository.newInstance();
			glAudit.setData(glAuditPo);
			glAudit.save();
		}else{
			
		}*/
	}
}
