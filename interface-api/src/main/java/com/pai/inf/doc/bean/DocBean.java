package com.pai.inf.doc.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DocBean {

	private String modelName; // 模块名字

	private String actionName; // action简称

	private String actionDesc; // action描述

	private String author; // 接口开发人

	private String createTime; // 创建时间

	private String updateTime; // 修改时间

	private String updateBy;// 修改人

	private String actionUrl; // 请求地址

	private String reqMethod;// 接口请求方式RequestMethod
	
	private String reqType;

	private String version;

	private String updateVersion; // 修改的版本
	
	private List<ParamField> params;

	private ParamBean request; // 参数

	private ParamBean response; // 响应参数

	private String[] modifyRecodes; // 修改记录

	private boolean deprecated; // 是否已停用

	private String stopVersion;// 接口停用的版本号
	
	private int sort;

	@Override
	public String toString() {
		return "版本：" + version + "\n模块名：" + modelName + "\n简称:" + actionName + "\n路径:" + actionUrl + "\n参数:"
				+ showParam() + "\n响应吗:" + response + "\n修改记录:" + showModifyRecods() + "\n-------------------------";
	}

	public String showParam() {
		boolean noParams = false;

		if (params != null && params.size() > 0) {

			List<String> reqParamList = null;

			StringBuffer sb = new StringBuffer();

			if (!noParams) {
				int i = 0;
				for (ParamField pf : params) {
					if (reqParamList == null || reqParamList.isEmpty()) {

						if (pf.isRequired()) {
							sb.append("*");
						} else {
							sb.append(" ");
						}

						sb.append(pf.getName() + "<");
						sb.append(pf.getType() + "> : ");
						sb.append(pf.getNote());

						if (++i < params.size()) {
							sb.append("\n");
						}
					} else {
						if (reqParamList.contains(pf.getName())) {
							if (pf.isRequired()) {
								sb.append("*");
							} else {
								sb.append(" ");
							}
							sb.append(pf.getName() + "<");
							sb.append(pf.getType() + "> : ");
							sb.append(pf.getNote());

							if (++i < params.size()) {
								sb.append("\n");
							}
						}
					}
				}
			}
			return sb.toString();
		}

		return "";
	}

	public String showModifyRecods() {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (modifyRecodes != null && StringUtils.isNotEmpty(modifyRecodes[0])) {
			for (String s : modifyRecodes) {
				if (++i < modifyRecodes.length)
					sb.append("·" + s + "\n");
				else
					sb.append("·" + s);
			}
		}

		return sb.toString();
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<ParamField> getParams() {
		return params;
	}

	public void setParams(List<ParamField> params) {
		this.params = params;
	}

	public ParamBean getRequest() {
		return request;
	}

	public void setRequest(ParamBean request) {
		this.request = request;
	}

	public ParamBean getResponse() {
		return response;
	}

	public void setResponse(ParamBean response) {
		this.response = response;
	}

	public String[] getModifyRecodes() {
		return modifyRecodes;
	}

	public void setModifyRecodes(String[] modifyRecodes) {
		this.modifyRecodes = modifyRecodes;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUpdateVersion() {
		return updateVersion;
	}

	public void setUpdateVersion(String updateVersion) {
		this.updateVersion = updateVersion;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getStopVersion() {
		return stopVersion;
	}

	public void setStopVersion(String stopVersion) {
		this.stopVersion = stopVersion;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
