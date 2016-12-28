package com.pai.biz.auth.domain;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pai.biz.frame.domain.AbstractDomain;
import com.pai.base.api.service.IdGenerator;
import com.pai.base.core.helper.SpringHelper;
import com.pai.base.core.util.string.StringUtils;
import com.pai.biz.auth.persistence.dao.AuthResourcesDao;
import com.pai.biz.auth.persistence.dao.AuthResourcesQueryDao;
import com.pai.biz.auth.persistence.dao.AuthRoleResourcesDao;
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
	
	@Resource
	private AuthResourcesQueryDao authResourcesQueryDao;
	
	@Resource
	private IdGenerator idGenerator;
	
	@Resource
	private AuthRoleResourcesDao authRoleResourcesDao;
	
	protected void init(){
		authResourcesDao = SpringHelper.getBean(AuthResourcesDao.class);
		setDao(authResourcesDao);
	}

	public void save(AuthResourcesPo authResourcesPo) {
		String id = idGenerator.genSid();
		StringBuilder sb = new StringBuilder();
		//查找父节点的树分支
		if(StringUtils.isNotEmpty(authResourcesPo.getParentId())){
			AuthResourcesPo parentResourcePo = authResourcesQueryDao.get(authResourcesPo.getParentId());
			if(StringUtils.isNotEmpty(parentResourcePo.getPath())){
				sb.append(parentResourcePo.getPath()).append(".").append(id);
				authResourcesPo.setPath(sb.toString());
			}
		}else {
			authResourcesPo.setPath(id);
			authResourcesPo.setDepth(1);
		}
		authResourcesPo.setId(id);
		this.setData(authResourcesPo);
		this.create();	
	}

	public void delete(String id) {
		this.destroy(id);
		//删除角色-资源关联
		authRoleResourcesDao.deleteByResourceId(id);
	}	 
	 
}
