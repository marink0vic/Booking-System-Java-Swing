package com.comtrade.constants;

public enum ServerResourcePath {
	
	SERVER_RESOURCES_PATH("C:/Users/marko/Desktop/Booking-System-Java-Swing/Server");
	
	private String path;
	
	private ServerResourcePath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
}
