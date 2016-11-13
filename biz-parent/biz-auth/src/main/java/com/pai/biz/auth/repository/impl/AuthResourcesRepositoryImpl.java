package com.pai.biz.auth.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.pai.base.core.helper.SpringHelper;
import com.pai.base.db.persistence.dao.IQueryDao;
import com.pai.biz.frame.repository.AbstractRepository;
import com.pai.biz.auth.domain.AuthResources;
import com.pai.biz.auth.repository.AuthResourcesRepository;
import com.pai.biz.auth.persistence.dao.AuthResourcesQueryDao;
import com.pai.biz.auth.persistence.entity.AuthResourcesPo;

/**
 * 对象功能:资源 Repository接口的实现类
 * 开发公司:PAI.COM
 * 开发人员:FUHAO
 * 创建时间:2016-08-07 14:07:40
 */
@Repository
public class AuthResourcesRepositoryImpl extends AbstractRepository<String, AuthResourcesPo,AuthResources> implements AuthResourcesRepository{
	  
	@Resource
	private  AuthResourcesQueryDao authResourcesQueryDao;

	public AuthResources newInstance() {
		AuthResourcesPo po = new AuthResourcesPo();
		AuthResources authResources = SpringHelper.getBean(AuthResources.class);
		authResources.setData(po);
		return authResources;
	}

	public AuthResources newInstance(AuthResourcesPo po) {
		AuthResources authResources = SpringHelper.getBean(AuthResources.class);
		authResources.setData(po);
		return authResources;
	} 
	
	@Override
	protected IQueryDao<String, AuthResourcesPo> getQueryDao() {
		return authResourcesQueryDao;
	}

	@Override
	public List<AuthResourcesPo> findResourcesByUserId(String userId) {
		return authResourcesQueryDao.findResourcesByUserId(userId);
	}
	
}
