package com.comtrade.constants;

public enum ImageFolder {

	SERVER_RESOURCES_PATH("C:/Users/marko/Desktop/Booking-System-Java-Swing/Server"),
	IMAGE_HOST_USER_FOLDER("./resources/images/users/hosts/"),
	IMAGE_REGULAR_USER_FOLDER("./resources/images/users/regular/");
	
	private String path;
	
	private ImageFolder(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
