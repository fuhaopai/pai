package com.pai.base.db.mybatis.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import com.pai.base.api.model.Page;
import com.pai.base.db.mybatis.impl.domain.MyBatisPage;
import com.pai.base.db.persistence.dao.AbstractQueryDao;
import com.pai.base.db.persistence.entity.AbstractPo;

@SuppressWarnings("serial")
public abstract class MyBatisQueryDaoImpl<PK extends java.io.Serializable,P extends AbstractPo<PK>> extends AbstractQueryDao<PK, P> implements Serializable{

	@Resource
    protected SqlSessionTemplate sqlSessionTemplate;
	
	public abstract String getNamespace();

	public P get(PK id) {
        @SuppressWarnings("unchecked")
		P selectOne = (P)sqlSessionTemplate.selectOne(getNamespace() + ".get", id);
		return selectOne;
	}

	public P getLast() {
        @SuppressWarnings("unchecked")
		P selectOne = (P)sqlSessionTemplate.selectOne(getNamespace() + ".getLast");
		return selectOne;
	}

	public Integer countAll() {
		String statement=getNamespace() + ".countAll";
		Object object = sqlSessionTemplate.selectOne(statement);
		return (Integer)object;
	}
	
	public Integer count(Map<String, Object> params) {
		String statement=getNamespace() + ".countAll";
		Object object = sqlSessionTemplate.selectOne(statement,params);
		return (Integer)object;
	}

	public Integer countByKey(String sqlKey, Map<String, Object> params) {
		String statement=getNamespace() + "." + sqlKey;
		Object object = sqlSessionTemplate.selectOne(statement,params);
		return (Integer)object;
	}

	public List<P> findAll() {
		return sqlSessionTemplate.selectList(getNamespace() + ".find", null);
	}
	
	public List<P> findAll(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getNamespace() + ".find", params);
	}

	public List<P> findPaged(Page page) {
		MyBatisPage defaultPage = (MyBatisPage)page;
		return sqlSessionTemplate.selectList(getNamespace() + ".find", null,defaultPage);
	}	

	public List<P> findPaged(Map<String, Object> params, Page page) {
		MyBatisPage defaultPage = (MyBatisPage)page;
		return sqlSessionTemplate.selectList(getNamespace() + ".find", params,defaultPage);
	}

	public List<P> findByIds(List<PK> ids) {
		if(ids.size()>0){
			Map<String, Object> map = b().a("ids", ids).p();
			return sqlSessionTemplate.selectList(getNamespace() + ".findByIds", map);
		}
		return new ArrayList<P>();
	}	
	
	/**
	 * 返回单条数据，如 select count(*) from table_a 
	 * @param sqlKey
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public P getByKey(String sqlKey,Object params)
	{
		return (P)sqlSessionTemplate.selectOne(getNamespace() + "." + sqlKey, params);
	}
    
    /**
     * 根据在Map配置文件中的Sql Key及参数获取实列表
     * @param sqlKey
     * @param params
     * @return
     */
    public List<P> findByKey(String sqlKey,Map<String,Object> params){
    	return sqlSessionTemplate.selectList(getNamespace() + "." + sqlKey , params);
    }
    
    /**
     * 根据在Map配置文件中的Sql Key及参数分页获取实列表
     * @param sqlKey
     * @param params
     * @param page
     * @return
     */
    public List<P> findByKey(String sqlKey,Map<String,Object> params,Page page){
    	return sqlSessionTemplate.selectList(getNamespace() + "." + sqlKey , params,new RowBounds(page.getStartIndex(), page.getPageSize()));
    }	
}
