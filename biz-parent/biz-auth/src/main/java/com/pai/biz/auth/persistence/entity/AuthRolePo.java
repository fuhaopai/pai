package com.pai.biz.auth.persistence.entity;

import java.util.List;

/**
 * 对象功能:角色 entity对象
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2016-12-29 09:36:49
 */
public class AuthRolePo extends AuthRoleTbl{
	
	private List<AuthResourcesPo> authResourcesPos;

	public List<AuthResourcesPo> getAuthResourcesPos() {
		return authResourcesPos;
	}

	public void setAuthResourcesPos(List<AuthResourcesPo> authResourcesPos) {
		this.authResourcesPos = authResourcesPos;
	}
	
	
}