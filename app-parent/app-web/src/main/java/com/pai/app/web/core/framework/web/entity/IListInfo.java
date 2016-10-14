package com.pai.app.web.core.framework.web.entity;

import java.util.List;

import com.pai.base.api.model.Page;

public interface IListInfo<P> extends Info{
	public List<P> getPoList();
	public Page getPage();
	public IQueryInfo getQueryInfo();
}
