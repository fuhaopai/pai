package com.pai.base.core.constants;

public enum StatusEnum {
	SUCCESS(1),
	FAILURE(2);
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	private StatusEnum(int status) {
		this.status = status;
	}
}
