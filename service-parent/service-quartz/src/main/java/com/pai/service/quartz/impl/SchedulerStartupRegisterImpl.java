package com.pai.service.quartz.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pai.base.core.helper.SpringHelper;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.quartz.JobPersistenceSupport;
import com.pai.service.quartz.SchedulerService;
import com.pai.service.quartz.SchedulerStartupRegister;
import com.pai.service.quartz.entity.IJobDefPo;
import com.pai.service.quartz.util.CronUtil;

public class SchedulerStartupRegisterImpl implements SchedulerStartupRegister{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	public void registerJobs(String group) {
		logger.info("============================================");
		logger.info("Enter SchedulerStartupRegisterImpl, register all jobs");
		JobPersistenceSupport jobPersistenceSupport = SpringHelper.getBean(JobPersistenceSupport.class);
		SchedulerService schedulerService = SpringHelper.getBean(SchedulerService.class);
		
		int registerJobCount = 0;
		if(schedulerService!=null && jobPersistenceSupport!=null){		
			List<IJobDefPo> jobDefPos = jobPersistenceSupport.findActivedJobDefPos(group);
			if(jobDefPos!=null){
				for(IJobDefPo jobDefPo:jobDefPos){
					if(StringUtils.isNotEmpty(jobDefPo.getExpr())){
						if(CronUtil.check(jobDefPo.getExpr())){
							schedulerService.startJob(jobDefPo);
							logger.info("#### register "+ jobDefPo.getName());
							registerJobCount++;	
						}else {
							logger.warn("$$$$ error expr + " + jobDefPo.getExpr());
						}
					}
				}
			}
		}
		logger.info("Complete SchedulerStartupRegisterImpl, register: " + registerJobCount + " Jobs");
		logger.info("============================================");
	}	

}
