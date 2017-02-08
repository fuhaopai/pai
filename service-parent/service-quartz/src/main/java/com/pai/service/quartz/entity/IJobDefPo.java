package com.pai.service.quartz.entity;

public interface IJobDefPo {
	public String getId();
	
	public String getName();

	public String getBean();
	
	public String getMethod();
	
	public String getGroup();
	
	public String getType();
	
	public String getExpr();
}
