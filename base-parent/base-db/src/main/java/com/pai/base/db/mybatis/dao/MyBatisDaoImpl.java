package com.pai.base.db.mybatis.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;

import com.pai.base.api.session.OnlineUserIdHolder;
import com.pai.base.core.util.string.StringUtils;
import com.pai.base.db.persistence.dao.AbstractDao;
import com.pai.base.db.persistence.entity.AbstractPo;

@SuppressWarnings("serial")
public abstract class MyBatisDaoImpl<PK extends java.io.Serializable,T extends AbstractPo<PK>> extends AbstractDao<PK, T> implements Serializable{

	@Resource
    protected SqlSessionTemplate sqlSessionTemplate;	
	
	public abstract String getNamespace();
	
	public void create(T entity) {
        if(entity instanceof AbstractPo){
        	AbstractPo<PK> po = (AbstractPo<PK>)entity; 
        	if(po.getCreateTime()==null){
        		  po.setCreateTime(new java.util.Date());
        	}
           if(StringUtils.isEmpty(po.getCreateBy())&&StringUtils.isNotEmpty(OnlineUserIdHolder.getUserId())){
            	po.setCreateBy(OnlineUserIdHolder.getUserId());
            }                      
        }        		
		sqlSessionTemplate.insert(getNamespace() + ".create", entity);
	}

	public void update(T entity) {
        if(entity instanceof AbstractPo){
        	AbstractPo<PK> po = (AbstractPo<PK>)entity;        	
            po.setUpdateTime(new java.util.Date());
           	po.setUpdateBy(OnlineUserIdHolder.getUserId());
        }        
        sqlSessionTemplate.update(getNamespace() + ".update", entity);        
	}

	public void updateByKey(String sqlKey, Map<String, Object> params) {
		sqlSessionTemplate.update(getNamespace() + "." + sqlKey, params);		
	}

	public void delete(PK id) {
		sqlSessionTemplate.delete(getNamespace() + ".delete", id);
	}

	public void deleteByKey(String sqlKey, Map<String, Object> params) {
		sqlSessionTemplate.delete(getNamespace() + "." + sqlKey, params);
	}

	public void deleteByIds(List<PK> ids) {
		for(PK id:ids){
			delete(id);
		}
	}

	public void updateByExampleSelective(T entity, Map<String, Object> params) {
		if(entity instanceof AbstractPo){
        	AbstractPo<PK> po = (AbstractPo<PK>)entity;        	
            po.setUpdateTime(new java.util.Date());
           	po.setUpdateBy(OnlineUserIdHolder.getUserId());
        }
		params.put("entity", entity);
		sqlSessionTemplate.update(getNamespace() + ".updateByExampleSelective", params);		
	}
}
