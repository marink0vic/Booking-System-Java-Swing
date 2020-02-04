package com.comtrade.util;

import java.awt.Image;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class ImageResize {
	
	private ImageResize() {
		throw new AssertionError();
	}

	public static Icon resizeImage(File selected_file, int width, int height) {
		ImageIcon myImage = new ImageIcon(selected_file.getAbsolutePath());
		Image img = myImage.getImage();
		Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
}
