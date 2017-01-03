package com.pai.biz.auth.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pai.biz.frame.domain.AbstractDomain;
import com.pai.base.core.helper.PasswordHelper;
import com.pai.base.core.helper.SpringHelper;
import com.pai.biz.auth.persistence.dao.AuthUserDao;
import com.pai.biz.auth.persistence.entity.AuthUserPo;

/**
 * 对象功能:用户 领域对象实体
 * 开发公司:PAI.COM
 * 开发人员:FUHAO
 * 创建时间:2016-09-29 18:00:11
 */
@SuppressWarnings("serial")
@Service
@Scope("prototype")
public class AuthUser extends AbstractDomain<String, AuthUserPo>{
	 
	 private AuthUserDao authUserDao = null;

	protected void init(){
		authUserDao = SpringHelper.getBean(AuthUserDao.class);
		setDao(authUserDao);
	}

	public void updatePassword(AuthUserPo authUserPo) {
		authUserPo.setPassword(PasswordHelper.getEncryptPassword(authUserPo.getName()+authUserPo.getPassword()));
		authUserDao.updatePassword(authUserPo);
	}

	public void save(AuthUserPo authUserPo) {
		
	}	 
	 
}
