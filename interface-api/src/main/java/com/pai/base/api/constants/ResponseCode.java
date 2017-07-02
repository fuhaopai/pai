package com.pai.base.api.constants;

public enum ResponseCode {

	OK("100", "处理成功"), 
	NOT_FOUND("404", "没有找到记录"),
	BUSINESS_EXCEPTION("900", "业务异常"), 
	BUSINESS_PARAMETER_EXCEPTION("905","参数异常"),
	UPLOAD_MAX_SIZE_EXCEPTION("902","上传图片超过大小限制"),
	SYSTEM_EXCEPTION("500", "系统异常");
	

	private String code;

	private String desc;

	private ResponseCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
