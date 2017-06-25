package com.pai.inf.doc.constants;

public enum DeveloperType {

	WUDIDI("吴迪迪"),

	FU_HAO("付浩"),
	
	MOYONGFENG("莫永丰"),
	
	CHENJUNMING("陈俊明"),
	
	FENGPINGPING("冯平平"),

	ZHANG_SI("张思"),
	
	MIAOJIFANG("苗继方"),
	
	QINYUNSEN("覃云森"),
	
	LUOYINZHONG("罗银忠"),
	
	HESIQIONG("何斯琼"),
	
	DEFAULT("");
	
	
	private final String name;

	DeveloperType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
