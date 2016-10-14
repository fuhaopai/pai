package com.pai.app.web.core.framework.web.create.entity;

import com.pai.base.api.model.IMsgVo;

public class RuleCreateMsg implements IMsgVo{
	private static final long serialVersionUID = 6951365332784844654L;
	public final static String MSG_TYPE="ruleCreate";
	
	@Override
	public String getMsgType() {
		return MSG_TYPE;
	}

	
}
