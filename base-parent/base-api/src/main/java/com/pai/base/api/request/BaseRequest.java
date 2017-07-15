package com.pai.base.api.request;
import java.io.Serializable;

import com.pai.base.api.annotion.AutoDocField;

public class BaseRequest implements Serializable{

	private static final long serialVersionUID = 4483948172643820880L;

	@AutoDocField(value = "来源系统", required = true)
	private String sourceSystem;

	@AutoDocField(value = "操作人/登录用户id", required = true)
	private String operator;

	@AutoDocField(value = "备注", required = false)
	private String memo;

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
