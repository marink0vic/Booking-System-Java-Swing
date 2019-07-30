package com.comtrade.constants;

public enum UserType {
	USER("USER"),
	SUPER_USER("SUPER_USER"),
	ADMIN("ADMIN");
	
	private String access;
	
	private UserType(String access) {
		this.access = access;
	}

	public String getAccess() {
		return access;
	}
	
}
