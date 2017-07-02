package com.pai.base.core.entity;

public class CommonResult {
	private boolean success = false;
	private String msgCode;
	private String msg;
	
	public CommonResult(){}
	
	public CommonResult(boolean success, String msgCode, String msg) {
		super();
		this.success = success;
		this.msgCode = msgCode;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
