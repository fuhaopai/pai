package com.pai.app.web.core.framework.web.entity;

import java.util.List;

import com.pai.base.api.model.Page;

public class DefaultListInfo<PO> implements IListInfo{
	private List<PO> poList;
	private Page page;
	private IQueryInfo queryInfo;
	
	public DefaultListInfo(List<PO> poList,Page page,IQueryInfo queryInfo){
		this.poList = poList;
		this.page = page;
		this.queryInfo = queryInfo;
	}
	
	public List<PO> getPoList() {
		return poList;
	}
	public Page getPage() {
		return page;
	}
	public IQueryInfo getQueryInfo() {
		return queryInfo;
	}
}
