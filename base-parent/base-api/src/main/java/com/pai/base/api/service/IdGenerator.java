package com.pai.base.api.service;

public interface IdGenerator {
	
	/* String型唯一的ID标识 */
	public String genSid();   
	
	/* Long型唯一的ID标识 */
	public Long genLid();           
	
	/* String GUID标识 */
	public String genUuid();
	
	/* 获取自增ID，所有服务器同步 */
	public Long genIncrId(String key,int min_value);	
}
