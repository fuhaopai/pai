package com.pai.biz.auth.repository.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.pai.base.core.helper.SpringHelper;
import com.pai.base.db.persistence.dao.IQueryDao;
import com.pai.biz.frame.repository.AbstractRepository;
import com.pai.biz.auth.domain.AuthRoleResources;
import com.pai.biz.auth.repository.AuthRoleResourcesRepository;
import com.pai.biz.auth.persistence.dao.AuthRoleResourcesQueryDao;
import com.pai.biz.auth.persistence.entity.AuthRoleResourcesPo;

/**
 * 对象功能:角色-授权 Repository接口的实现类
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2016-12-28 11:56:47
 */
@Repository
public class AuthRoleResourcesRepositoryImpl extends AbstractRepository<String, AuthRoleResourcesPo,AuthRoleResources> implements AuthRoleResourcesRepository{
	  
	@Resource
	private  AuthRoleResourcesQueryDao authRoleResourcesQueryDao;

	public AuthRoleResources newInstance() {
		AuthRoleResourcesPo po = new AuthRoleResourcesPo();
		AuthRoleResources authRoleResources = SpringHelper.getBean(AuthRoleResources.class);
		authRoleResources.setData(po);
		return authRoleResources;
	}

	public AuthRoleResources newInstance(AuthRoleResourcesPo po) {
		AuthRoleResources authRoleResources = SpringHelper.getBean(AuthRoleResources.class);
		authRoleResources.setData(po);
		return authRoleResources;
	} 
	
	@Override
	protected IQueryDao<String, AuthRoleResourcesPo> getQueryDao() {
		return authRoleResourcesQueryDao;
	}
	
}
