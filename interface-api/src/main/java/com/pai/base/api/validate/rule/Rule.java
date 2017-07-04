package com.pai.base.api.validate.rule;

/**
 * 校验规则，用于扩展校验规则
 * @author fuhao
 *
 */
public interface Rule {
	public String getMessage();
	public boolean isValid();
}