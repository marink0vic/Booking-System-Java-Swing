package com.comtrade.view.user.regular.property;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.comtrade.domain.Address;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.dto.PropertyWrapper;

public class PropertyInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private PropertyWrapper propertyWrapper;
	private Property property;
	private Address address;
	private List<PropertyImage> propertyImages;
	
	private JLabel lblMainImage;
	private final static int DIMENSION = 120;
	private JLabel lblPropertyName;
	private JLabel lblAddress;
	private JLabel lblLocation;
	private JLabel lblGrade;
	private JLabel lblHelper1;

	
	public PropertyInfoPanel(PropertyWrapper propertyWrapper) {
		this.propertyWrapper = propertyWrapper;
		this.property = propertyWrapper.getProperty();
		this.address = propertyWrapper.getAddress();
		this.propertyImages = propertyWrapper.getImages();
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(252, 165, 975, 767);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		lblPropertyName = new JLabel(property.getName());
		lblPropertyName.setForeground(new Color(71, 71, 71));
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPropertyName.setBounds(0, 0, 288, 62);
		this.add(lblPropertyName);
		
		lblAddress = new JLabel("");
		lblAddress.setForeground(new Color(71, 71, 71));
		lblAddress.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblAddress.setBounds(0, 13, 353, 95);
		this.add(lblAddress);
		String street = address.getStreet() + " " + address.getNumber();
		String city = address.getZipCode() + " " + address.getCity();
		lblAddress.setText(street + ", " + city + ", " + propertyWrapper.getCountry().getName());
		
		lblLocation = new JLabel("map location");
		lblLocation.setForeground(new Color(71, 71, 71));
		lblLocation.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblLocation.setBounds(0, 65, 288, 62);
		this.add(lblLocation);
		
		lblGrade = new JLabel("10");
		lblGrade.setOpaque(true);
		lblGrade.setForeground(new Color(255, 255, 255));
		lblGrade.setBackground(new Color(9, 121, 186));
		lblGrade.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrade.setFont(new Font("Dialog", Font.BOLD, 24));
		lblGrade.setBounds(0, 121, 56, 48);
		this.add(lblGrade);
		
		lblHelper1 = new JLabel("What guests loved the most:");
		lblHelper1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblHelper1.setForeground(new Color(71, 71, 71));
		lblHelper1.setBounds(0, 223, 272, 32);
		this.add(lblHelper1);
		
		JTextArea reviewOne = new JTextArea();
		reviewOne.setBackground(new Color(173, 216, 230));
		reviewOne.setBounds(0, 278, 315, 192);
		this.add(reviewOne);
		
		JTextArea reviewTwo = new JTextArea();
		reviewTwo.setBackground(new Color(173, 216, 230));
		reviewTwo.setBounds(0, 483, 315, 192);
		this.add(reviewTwo);
		
		JLabel lblCheckAllReviews = new JLabel("Check all reviews:");
		lblCheckAllReviews.setForeground(new Color(71, 71, 71));
		lblCheckAllReviews.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckAllReviews.setBounds(0, 688, 197, 32);
		this.add(lblCheckAllReviews);
		
		JTextArea txtAboutProperty = new JTextArea();
		txtAboutProperty.setFont(new Font("Dialog", Font.BOLD, 18));
		txtAboutProperty.setForeground(new Color(71, 71, 71));
		txtAboutProperty.setBounds(379, 524, 596, 177);
		txtAboutProperty.setText(property.getDescription());
		txtAboutProperty.setLineWrap(true);
		txtAboutProperty.setWrapStyleWord(true);
		this.add(txtAboutProperty);
		
		addImagesToLabels();
	}
	
	private void addImagesToLabels() {
		lblMainImage = new JLabel("");
		lblMainImage.setBackground(new Color(64, 224, 208));
		lblMainImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainImage.setOpaque(true);
		lblMainImage.setBounds(379, 0, 596, 347);
		String mainImagePath = propertyImages.get(0).getImage();
		lblMainImage.setIcon(resizeImage(mainImagePath, lblMainImage.getWidth(), lblMainImage.getHeight()));
		this.add(lblMainImage);
		
		JScrollPane scrollPanelSmallGallery = new JScrollPane();
		scrollPanelSmallGallery.setBounds(379, 380, 596, 110);
		scrollPanelSmallGallery.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPanelSmallGallery.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollPanelSmallGallery);
		
		JPanel smallImagePanel = new JPanel();
		scrollPanelSmallGallery.setViewportView(smallImagePanel);
		
		for (PropertyImage image : propertyImages) {
			smallImagePanel.add(smallGalleryPanels(smallImagePanel, image.getImage()));
		}
	}
	
	private JPanel smallGalleryPanels(JPanel smallImagePanel,String image) {
		Icon icon = resizeImage(image, DIMENSION, DIMENSION);
		JPanel newPanel = new JPanel();
        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(DIMENSION,DIMENSION));
        lbl.setIcon(icon);
        newPanel.add(lbl);
        newPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblMainImage.setIcon(resizeImage(image, lblMainImage.getWidth(), lblMainImage.getHeight()));
			}
		});
        return newPanel;
	}
	
	private Icon resizeImage(String imgPath, int width, int height) {
		File file = new File(imgPath);
		Image image = new ImageIcon(file.getPath()).getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}

}
