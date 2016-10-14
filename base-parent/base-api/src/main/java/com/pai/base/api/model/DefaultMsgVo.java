package com.pai.base.api.model;

@SuppressWarnings("serial")
public class DefaultMsgVo implements IMsgVo{

	public final static String MSG_TYPE="console";
	
	private String msg;
	
	public String getMsgType() {
		return MSG_TYPE;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
