package com.pai.app.web.core.framework.util;

import javax.servlet.http.HttpServletRequest;

import com.pai.app.web.core.framework.web.entity.IQueryInfo;
import com.pai.app.web.core.framework.web.entity.QueryInfo;

public class QueryUtil {
	public static IQueryInfo buildQueryInfo(HttpServletRequest request){
		QueryInfo queryInfo = new QueryInfo();
		return queryInfo;
	}
}
