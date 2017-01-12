package com.pai.biz.auth.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pai.biz.frame.domain.AbstractDomain;
import com.pai.base.core.helper.SpringHelper;
import com.pai.biz.auth.persistence.dao.AuthRoleDao;
import com.pai.biz.auth.persistence.entity.AuthRolePo;

/**
 * 对象功能:角色 领域对象实体
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2016-12-29 09:36:49
 */
@SuppressWarnings("serial")
@Service
@Scope("prototype")
public class AuthRole extends AbstractDomain<String, AuthRolePo>{
	 
	 private AuthRoleDao authRoleDao = null;

	protected void init(){
		authRoleDao = SpringHelper.getBean(AuthRoleDao.class);
		setDao(authRoleDao);
	}

	public void save(AuthRolePo authRolePo) {
		
	}	 
	 
}
