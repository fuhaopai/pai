package com.pai.base.api.doc.constants;

public enum DeveloperType {

	FU_HAO("付浩"),
	
	DEFAULT("");
	
	
	private final String name;

	DeveloperType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
