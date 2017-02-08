package com.pai.service.quartz.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.pai.base.core.helper.SpringHelper;
import com.pai.base.core.util.date.DateConverter;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.quartz.BaseJob;
import com.pai.service.quartz.JobPersistenceSupport;
import com.pai.service.quartz.SchedulerService;
import com.pai.service.quartz.constants.JobConstants;
import com.pai.service.quartz.constants.JobConstants.EXECUTE;
import com.pai.service.quartz.entity.IJobDefPo;
import com.pai.service.quartz.entity.IJobParamPo;
import com.pai.service.quartz.util.CronUtil;

@Service
public class SchedulerServiceImpl implements SchedulerService,InitializingBean{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	Scheduler scheduler;

	JobPersistenceSupport jobPersistenceSupport;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		scheduler.start();
	}

	public boolean startJob(String jobDefId) {
		boolean result = false;
		jobPersistenceSupport = SpringHelper.getBean(JobPersistenceSupport.class);		
		if(jobPersistenceSupport!=null){
			IJobDefPo jobDefPo = jobPersistenceSupport.getJobDefPo(jobDefId);
			result = startJob(jobDefPo);
		}
		return result;
	}
	private final static String LOCK = "lock";
	public boolean startJob(IJobDefPo jobDefPo) {
		boolean isStart = false;
		if(jobDefPo!=null && StringUtils.isNotEmpty(jobDefPo.getBean()) && CronUtil.check(jobDefPo.getExpr())){
			synchronized (StringUtils.isEmpty(jobDefPo.getId())?LOCK:jobDefPo.getId()) {							
				logger.info("Enter start Job method，name="+jobDefPo.getName());
				isStart = startPlan(jobDefPo.getId(),jobDefPo.getBean(),jobDefPo.getGroup());
				if(!isStart){
					List<IJobParamPo> jobParamPos = jobPersistenceSupport.findParams(jobDefPo.getId());
					JobDetail jobDetail = null;
		
					BaseJob jobBean = (BaseJob) SpringHelper.getBean(jobDefPo.getBean());	
					if(jobBean!=null){
						jobDetail = newJob(jobBean.getClass()).withIdentity(getKey(jobDefPo), jobDefPo.getGroup()).storeDurably(false).build();
					}else{
						return false;
					}
					//构造参数，取jobParamPos表的key,value属性值存入map中
					Map<String, Object> dataMap = buildDataMap(jobParamPos);
					jobDetail.getJobDataMap().putAll(dataMap);
					CronTrigger	trigger = buildCronTrigger(getKey(jobDefPo),jobDefPo.getGroup(),jobDefPo.getExpr());
					try {
						isStart = startPlan(jobDefPo.getId(),jobDefPo.getBean(),jobDefPo.getGroup());
						if(!isStart){				
							deleteJob(getKey(jobDefPo), jobDefPo.getGroup());
							scheduler.scheduleJob(jobDetail,trigger);
							logger.info("Start Job Success:"+jobDefPo.getName());
						}
						return true;
					} catch (SchedulerException e) {			
						e.printStackTrace();
						logger.error("Start Job Error:"+jobDefPo.getName() + " " +e.getMessage());
						return false;
					}
				}else {
					if(!CronUtil.check(jobDefPo.getExpr())){
						logger.info("Job Expr Error! :"+jobDefPo.getExpr());
					}else {
						logger.info("Job has run:"+jobDefPo.getName());	
					}				
				}
			}
		}
		return isStart;
	}

	@Override
	public boolean startJob(String bean, String group, String expr,
			List<IJobParamPo> jobParamPos) {
		logger.info("startJob bean="+bean + ";group="+group+";expr="+expr+";jobParamPos="+jobParamPos);
		if(!CronUtil.check(expr)){
			logger.warn("expr error!!! : " + expr);
			return false;
		}
		BaseJob jobBean = (BaseJob) SpringHelper.getBean(bean);
		JobDetail job = getJobDetail(bean, group);
		if(job==null){
			job = newJob(jobBean.getClass()).withIdentity(bean + "_" + UUID.randomUUID(), group).storeDurably(false).build();
		}		
		Map<String, Object> dataMap = buildDataMap(jobParamPos);
		job.getJobDataMap().putAll(dataMap);
		CronTrigger	trigger = buildCronTrigger(UUID.randomUUID().toString(),group,expr);
		try {
			scheduler.scheduleJob(job,trigger);
			return true;
		} catch (SchedulerException e) {			
			e.printStackTrace();
			logger.error("startJob(String bean, String group, String expr,List<IJobParamPo> jobParamPos) error"+e.getMessage(),e);
			return false;
		}		
	}

	@Override
	public boolean startOneTime(String jobDefId,String beanId,String group,List<IJobParamPo> jobParamPos) {
		logger.info("Enter startOneTime method，beanId="+beanId+"; group="+group);
		JobDetail job = getJobDetail(beanId + "_" + UUID.randomUUID(), group);
		boolean jobExist = true;
		if(job==null){
			jobExist = false;
			job = buildJobDetail(jobDefId,beanId, group, null,false);
		}
		if(job!=null && jobParamPos!=null && jobParamPos.size()>0){
			Map<String, Object> dataMap = buildDataMap(jobParamPos);
			job.getJobDataMap().putAll(dataMap);
		}
		try {		
			SimpleTrigger trigger = buildSimpleTrigger(job);
			if(jobExist){
				scheduler.scheduleJob(trigger);
			}else {
				scheduler.scheduleJob(job,trigger);	
			}	
			logger.info("startOneTime success!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("startOneTime error!"+e.getMessage(),e);
			return false;
		}		
	}

	@Override
	public boolean shutdownPlan(String planId,String bean, String group) {
		TriggerKey triggerKey = new TriggerKey(getKey(bean,planId),group);
		try {
			boolean isStop = false;
			Trigger.TriggerState state= scheduler.getTriggerState(triggerKey);
			if(state==TriggerState.NORMAL 
					|| state == TriggerState.BLOCKED){
				scheduler.pauseTrigger(triggerKey);
				isStop = true;
			}	
			if(!isStop){
				deleteJob(getKey(bean, planId), group);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	private void deleteJob(String key, String group){
		JobKey jobKey = new JobKey(key, group);
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean startPlan(String planId, String bean,String group){
		TriggerKey triggerKey = new TriggerKey(getKey(bean,planId),group);
		try {
			Trigger.TriggerState state= scheduler.getTriggerState(triggerKey);
			if(state!=null){
				if(state==TriggerState.PAUSED || state==TriggerState.BLOCKED){
					scheduler.resumeTrigger(triggerKey);
					return true;
				}else if(state==TriggerState.NONE || state==TriggerState.COMPLETE || state==TriggerState.ERROR){
					JobKey jobKey = new JobKey(getKey(bean, planId), group);
					scheduler.deleteJob(jobKey);					
				}
			}		
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;		
	}

	@Override
	public TriggerState getTriggerState(String planId,String bean, String group) {
		TriggerKey triggerKey = new TriggerKey(getKey(bean,planId),group);
		try {
			Trigger.TriggerState state= scheduler.getTriggerState(triggerKey);
			return state;	
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void startScheduler() {
		try {
			init();
			if(!scheduler.isStarted()){
				scheduler.start();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void shutdownScheduler() {
		try {
			init();
			if(!scheduler.isShutdown()){
				scheduler.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void init(){
		if(scheduler==null ){
			scheduler = (Scheduler) SpringHelper.getBean("scheduler");
		}
		if(jobPersistenceSupport==null){
			jobPersistenceSupport = SpringHelper.getBean(JobPersistenceSupport.class);
		}
		if(scheduler==null){
			throw new RuntimeException("Can't get scheduler instance, please check service-quartz.xml file.");
		}
	}
	
	private JobDetail getJobDetail(String beanId,String group){
		JobKey jobKey = new JobKey(beanId, group);
		try {
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if(jobDetail!=null){
				return jobDetail;
			}
		} catch (SchedulerException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	private SimpleTrigger buildSimpleTrigger(JobDetail job){
		SimpleTrigger trigger = (SimpleTrigger) newTrigger().forJob(job) 
                .startAt(new Date()).withSchedule(simpleSchedule().withIntervalInSeconds(1) 
                        .withRepeatCount(0) 
                        ) 
                        .build();
		return trigger;
	}	
	
	private CronTrigger buildCronTrigger(String planId,String group,String expr){
		CronTrigger	trigger = TriggerBuilder.newTrigger().withIdentity(planId, group)  
                .withSchedule(CronScheduleBuilder.cronSchedule(expr)).build();
		return trigger;
	}
	
	private JobDetail buildJobDetail(String jobDefId,String beanId,String group,List<IJobParamPo> jobParamPos,boolean isDurable){
		BaseJob jobBean = (BaseJob) SpringHelper.getBean(beanId);
		JobDetail jobDetail = newJob(jobBean.getClass()).withIdentity(getKey(beanId, jobDefId), group).storeDurably(isDurable).build();
		if(jobParamPos!=null && jobParamPos.size()>0){
			jobDetail.getJobDataMap().putAll(buildDataMap(jobParamPos));
		}
		return jobDetail;
	}
	
	private Map<String, Object> buildDataMap(List<IJobParamPo> jobParamPos){		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(jobParamPos==null){
			return dataMap;
		}
		for(IJobParamPo jobParamPo:jobParamPos){
			if(JobConstants.DATA_TYPE.INT.equals(jobParamPo.getType())){
				dataMap.put(jobParamPo.getKey(), Integer.parseInt(jobParamPo.getValue()));
			}else if(JobConstants.DATA_TYPE.LONG.equals(jobParamPo.getType())){
				dataMap.put(jobParamPo.getKey(), Long.parseLong(jobParamPo.getValue()));
			}else if(JobConstants.DATA_TYPE.DOUBLE.equals(jobParamPo.getType())){
				dataMap.put(jobParamPo.getKey(), Double.parseDouble(jobParamPo.getValue()));
			}else if(JobConstants.DATA_TYPE.DATE.equals(jobParamPo.getType())){
				try {
					dataMap.put(jobParamPo.getKey(), DateConverter.toDate(jobParamPo.getValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				dataMap.put(jobParamPo.getKey(), jobParamPo.getValue());
			}
		}
		return dataMap;
	}
	private List<Object> buildParams(List<IJobParamPo> jobParamPos){
		List<Object> params = new ArrayList<Object>();
		for(IJobParamPo jobParamPo:jobParamPos){
			if(JobConstants.DATA_TYPE.INT.equals(jobParamPo.getType())){
				params.add(Integer.parseInt(jobParamPo.getValue()));
			}else if(JobConstants.DATA_TYPE.LONG.equals(jobParamPo.getType())){
				params.add(Long.parseLong(jobParamPo.getValue()));
			}else if(JobConstants.DATA_TYPE.DOUBLE.equals(jobParamPo.getType())){
				params.add(Double.parseDouble(jobParamPo.getValue()));
			}else if(JobConstants.DATA_TYPE.DATE.equals(jobParamPo.getType())){
				try {
					params.add(DateConverter.toDate(jobParamPo.getValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}				
			}else{
				params.add(jobParamPo.getValue());
			}
		}
		return params;
	}
	private List<Class> buildParamTypes(List<IJobParamPo> jobParamPos){
		List<Class> paramTypes = new ArrayList<Class>();
		for(IJobParamPo jobParamPo:jobParamPos){
			if(JobConstants.DATA_TYPE.INT.equals(jobParamPo.getType())){
				paramTypes.add(Integer.class);
			}else if(JobConstants.DATA_TYPE.LONG.equals(jobParamPo.getType())){
				paramTypes.add(Long.class);
			}else if(JobConstants.DATA_TYPE.DOUBLE.equals(jobParamPo.getType())){
				paramTypes.add(Double.class);
			}else if(JobConstants.DATA_TYPE.DATE.equals(jobParamPo.getType())){
				paramTypes.add(Date.class);				
			}else{
				paramTypes.add(String.class);
			}
		}
		return paramTypes;
	}
	
	private void prepare(JobDetail jobDetail,IJobDefPo jobDefPo,List<IJobParamPo> jobParamPos){
		jobDetail.getJobDataMap().put(EXECUTE.TASK_NAME, jobDefPo.getName());
		jobDetail.getJobDataMap().put(EXECUTE.METHOD_NAME, jobDefPo.getMethod());
		jobDetail.getJobDataMap().put(EXECUTE.CLASS_NAME, jobDefPo.getBean());
		if(jobParamPos!=null && jobParamPos.size()>0){
			jobDetail.getJobDataMap().put(EXECUTE.PARAM_TYPES, buildParamTypes(jobParamPos));
			jobDetail.getJobDataMap().put(EXECUTE.PARAMS, buildParams(jobParamPos));
		}
	}	
	
	private String getKey(IJobDefPo jobDefPo){
		return jobDefPo.getBean() + "_" + jobDefPo.getId();
	}
	private String getKey(String bean,String jobDefId){
		return bean + "_" + jobDefId;
	}
}
