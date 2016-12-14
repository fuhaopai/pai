package com.pai.biz.auth.persistence.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pai.base.db.persistence.entity.TreeType;

/**
 * 对象功能:资源 entity对象
 * 开发公司:PAI.COM
 * 开发人员:FUHAO
 * 创建时间:2016-08-07 14:07:40
 */
public class AuthResourcesPo extends AuthResourcesTbl implements TreeType<AuthResourcesPo>{

	private List<AuthResourcesPo> subs = new ArrayList<AuthResourcesPo>();
	
	@Override
	public List<AuthResourcesPo> getSubs() {
		return Collections.unmodifiableList(subs);
	}

	@Override
	public void addSub(AuthResourcesPo po) {
		subs.add(po);
	}
	
	//对字段type枚举判断，菜单用于查询显示，按钮用于权限拦截
	public static enum ResourceType{
		MENU(1), //菜单
		BUTTON(2); //按钮
		private ResourceType(Integer type){
			this.type = type;
		}
		private Integer type;
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
	}
}