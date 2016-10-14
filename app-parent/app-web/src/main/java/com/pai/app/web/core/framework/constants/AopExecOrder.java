package com.pai.app.web.core.framework.constants;

public enum AopExecOrder {

	BEFORE("before"), 
	AFTER("after"), 
	EXCEPTION("exception");

	public String name;

	private AopExecOrder(String order) {
		this.name = order;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
