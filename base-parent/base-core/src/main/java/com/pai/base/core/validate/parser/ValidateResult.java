package com.pai.base.core.validate.parser;

/**
 * 校验结果
 * 用isValid判断校验是否通过
 * 如果校验没通过，用getMessage()得到错误消息
 * @author fuhao
 *
 */
public class ValidateResult {
	public ValidateResult() {
		this.isValid = true;
	}
	
	private boolean isValid;	// 是否通过
	private String message;		// 错误信息
	
	/**
	 * 获取校验结果消息
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	
	public void setMessage(String message) {
		this.isValid = false;
		this.message = message;
	}
	
	/**
	 * 校验是否通过
	 * @return true	通过
	 * 			false 没通过
	 */
	public boolean isValid() {
		return isValid;
	}
}
