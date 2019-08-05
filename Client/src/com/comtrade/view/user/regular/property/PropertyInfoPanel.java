package com.comtrade.view.user.regular.property;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.Address;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.User;
import com.comtrade.dto.Message;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;

import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class PropertyInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private User user;
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
	private JTextArea messageArea;
	private JTextArea chatArea;
	private String messageText;

	
	public PropertyInfoPanel(PropertyWrapper propertyWrapper, User user) {
		this.propertyWrapper = propertyWrapper;
		this.user = user;
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
		
		lblGrade = new JLabel(setPropertyRating());
		lblGrade.setOpaque(true);
		lblGrade.setForeground(new Color(255, 255, 255));
		lblGrade.setBackground(new Color(9, 121, 186));
		lblGrade.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrade.setFont(new Font("Dialog", Font.BOLD, 24));
		lblGrade.setBounds(0, 121, 56, 48);
		this.add(lblGrade);
		
		lblHelper1 = new JLabel("If you have any questions send us a message");
		lblHelper1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblHelper1.setForeground(new Color(71, 71, 71));
		lblHelper1.setBounds(0, 223, 353, 32);
		this.add(lblHelper1);
		
		messageArea = new JTextArea();
		messageArea.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		messageArea.setFont(new Font("Dialog", Font.BOLD, 18));
		messageArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					messageText = messageArea.getText();
					if (messageText.length() > 0) {
						sendMessage();
					}
				}
			}
		});
		messageArea.setBackground(new Color(255, 255, 255));
		messageArea.setForeground(ColorConstants.GRAY);
		messageArea.setBounds(0, 278, 315, 85);
		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);
		this.add(messageArea);
		
		chatArea = new JTextArea();
		chatArea.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		chatArea.setForeground(ColorConstants.GRAY);
		chatArea.setFont(new Font("Dialog", Font.BOLD, 18));
		chatArea.setBackground(new Color(255, 255, 255));
		chatArea.setBounds(0, 380, 315, 288);
		chatArea.setEditable(false);
		chatArea.setWrapStyleWord(true);
		chatArea.setLineWrap(true);
		this.add(chatArea);
		
		JTextArea txtAboutProperty = new JTextArea();
		txtAboutProperty.setFont(new Font("Dialog", Font.BOLD, 18));
		txtAboutProperty.setForeground(new Color(71, 71, 71));
		txtAboutProperty.setBounds(379, 524, 596, 177);
		txtAboutProperty.setText(property.getDescription());
		txtAboutProperty.setLineWrap(true);
		txtAboutProperty.setWrapStyleWord(true);
		txtAboutProperty.setEditable(false);
		this.add(txtAboutProperty);
		
		JButton btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				messageText = messageArea.getText();
				if (messageText.length() > 0) {
					sendMessage();
				}
			}
		});
		btnSend.setForeground(Color.WHITE);
		btnSend.setBackground(ColorConstants.BLUE);
		btnSend.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSend.setBounds(62, 688, 194, 48);
		add(btnSend);
		
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
	
	private String setPropertyRating() {
		int score = 0;
		int count = 0;
		for (PropertyReview pr : propertyWrapper.getReviews()) {
			score += pr.getRating();
			count++;
		}
		if (score == 0) return "--";
		double avg = (double) score / count;
		String total = String.format("%.2f", avg);
		return total;
	}
	
	private void sendMessage() {
		Message message = new Message(user, propertyWrapper.getUser(), messageText);
		TransferClass transfer = new TransferClass();
		transfer.setClientRequest(message);
		transfer.setDomainType(DomainType.USER);
		transfer.setOperation(Operations.MESSAGE);
		
		chatArea.append("Me: " + messageText + "\n");
		messageArea.setText("");
		ControllerUI.getController().sendToServer(transfer);
	}

	public void showMessageToTextArea(Message message) {
		User userSender = message.getSender();
		String name = userSender.getFirstName() + " " + userSender.getLastName() + ": ";
		String ms = name + message.getMessage();
		chatArea.append(ms + "\n");
	}
}
