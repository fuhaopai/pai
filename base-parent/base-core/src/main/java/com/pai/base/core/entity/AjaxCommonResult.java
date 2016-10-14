package com.pai.base.core.entity;

public class AjaxCommonResult extends CommonResult {
	private Object data;
	private boolean isLastRow;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isLastRow() {
		return isLastRow;
	}

	public void setLastRow(boolean isLastRow) {
		this.isLastRow = isLastRow;
	}

}
