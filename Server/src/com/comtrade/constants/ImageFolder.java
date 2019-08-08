package com.comtrade.constants;

public enum ImageFolder {

	IMAGE_HOST_USER_FOLDER("./resources/images/users/hosts/"),
	IMAGE_REGULAR_USER_FOLDER("./resources/images/users/regular/"),
	IMAGE_DEFAULT_USER_PROFILE_PICTURE("/resources/images/users/user-icon.jpg");
	
	private String path;
	
	private ImageFolder(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
