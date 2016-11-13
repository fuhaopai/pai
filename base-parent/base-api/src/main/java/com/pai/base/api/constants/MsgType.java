package com.pai.base.api.constants;
public enum MsgType {
	DEFAULT("default","默认普通消息"),		//直接在Console打印出
	SMS("sms","短信"),
	INNER("inner","站内消息"),
	EMAIL("email","邮件");
	private String key;
	private String label;
	MsgType(String key,String label){
		this.key = key;
		this.label = label;
	}
	public String key(){
		return key;		
	}
	public String label(){
		return label;		
	}	
}
