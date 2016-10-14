package com.pai.biz.auth.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pai.biz.frame.domain.AbstractDomain;
import com.pai.base.core.helper.SpringHelper;
import com.pai.biz.auth.persistence.dao.AuthResourcesDao;
import com.pai.biz.auth.persistence.entity.AuthResourcesPo;

/**
 * 对象功能:资源 领域对象实体
 * 开发公司:PAI.COM
 * 开发人员:FUHAO
 * 创建时间:2016-08-07 14:07:40
 */
@SuppressWarnings("serial")
@Service
@Scope("prototype")
public class AuthResources extends AbstractDomain<String, AuthResourcesPo>{
	 
	 private AuthResourcesDao authResourcesDao = null;

	protected void init(){
		authResourcesDao = SpringHelper.getBean(AuthResourcesDao.class);
		setDao(authResourcesDao);
	}	 
	 
}
