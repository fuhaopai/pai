package com.pai.app.web.core.framework.web.valid.entity;

import java.io.Serializable;
import java.util.Map;

import com.pai.base.api.model.IMsgVo;

public class SmsValidCodeMsg implements IMsgVo, Serializable{
	private static final long serialVersionUID = 3941438978452495762L;

	public final static String MSG_TYPE="smsValidCode"; 
	
	private String bizCode;
	private String mobile;
	private String sendType;
	private String sendMsg;
//	private ReacDetailPo reacDetailPo; //会员激活推送
	private Map<String, Object> modelMap;
	private String fromType;	//项目来源
	
	
	@Override
	public String getMsgType() {
		return MSG_TYPE;
	}


	public String getBizCode() {
		return bizCode;
	}


	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;	
	}


	public Map<String, Object> getModelMap() {
		return modelMap;
	}


	public void setModelMap(Map<String, Object> modelMap) {
		this.modelMap = modelMap;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getSendType() {
		return sendType;
	}


	public void setSendType(String sendType) {
		this.sendType = sendType;
	}


	public String getSendMsg() {
		return sendMsg;
	}


	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}


/*	public ReacDetailPo getReacDetailPo() {
		return reacDetailPo;
	}


	public void setReacDetailPo(ReacDetailPo reacDetailPo) {
		this.reacDetailPo = reacDetailPo;
	}*/


	public String getFromType() {
		return fromType;
	}


	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	

}
