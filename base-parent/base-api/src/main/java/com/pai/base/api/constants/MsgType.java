package com.pai.base.api.constants;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-sys-api
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-23-下午3:30:19
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
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
