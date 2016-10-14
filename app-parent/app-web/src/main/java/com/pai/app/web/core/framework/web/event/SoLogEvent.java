package com.pai.app.web.core.framework.web.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class SoLogEvent extends ApplicationEvent{
	
	private String partyType;	//参与者类型。platform=平台管理人员；member=会员；system=系统自动操作（此类型时，参与者ID可以为空）

	private String preStatus;	//订单修改前的状态
	
	private String curStatus;	//订单当前状态
	
	private String prePayStatus;	//支付前状态
	
	private String curPayStatus;	//支付当前状态
	
	public SoLogEvent(Object source) {
		super(source);
	}

	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

	public String getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getPrePayStatus() {
		return prePayStatus;
	}

	public void setPrePayStatus(String prePayStatus) {
		this.prePayStatus = prePayStatus;
	}

	public String getCurPayStatus() {
		return curPayStatus;
	}

	public void setCurPayStatus(String curPayStatus) {
		this.curPayStatus = curPayStatus;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

}
