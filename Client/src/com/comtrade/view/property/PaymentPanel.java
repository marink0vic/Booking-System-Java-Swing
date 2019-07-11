package com.comtrade.view.property;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.Address;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;

public class PaymentPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JLabel lblChosePaymentOption;
	private JCheckBox cb1;
	private JLabel lbl1;
	private JLabel lbl2;
	private JCheckBox cb2;
	private JLabel lbl3;
	private JCheckBox cb3;
	private JLabel lbl6;
	private JCheckBox cb6;
	private JLabel lbl5;
	private JCheckBox cb5;
	private JLabel lbl4;
	private JCheckBox cb4;
	private JButton btnFinishRegistration;
	private List<JLabel> labelList;
	private Map<Integer,PaymentType> map;
	
	//---
	private User user;
	private Address address;
	private Property property;
	private Map<RoomType, RoomInfo> room;
	private List<File> images; 
	private List<PaymentType> paymentList;

	
	public PaymentPanel(User user, Address address, Property property, Map<RoomType, RoomInfo> room, List<File> images) {
		this.user = user;
		this.address = address;
		this.property = property;
		this.room = room;
		this.images = images;
		initializeComponents();
	}


	private void initializeComponents() {
		labelList = new ArrayList<>();
		map = new HashMap<>();
		
		this.setBounds(0, 120, 1282, 783);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		lblChosePaymentOption = new JLabel("Chose payment option");
		lblChosePaymentOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblChosePaymentOption.setForeground(new Color(71, 71, 71));
		lblChosePaymentOption.setFont(new Font("Dialog", Font.PLAIN, 23));
		lblChosePaymentOption.setBounds(492, 105, 300, 80);
		this.add(lblChosePaymentOption);
		
		cb1 = new JCheckBox("");
		cb1.setBackground(new Color(255, 255, 255));
		cb1.setBounds(368, 262, 31, 32);
		this.add(cb1);
		
		lbl1 = new JLabel("test");
		lbl1.setBorder(null);
		lbl1.setBounds(415, 260, 96, 44);
		this.add(lbl1);
		
		lbl2 = new JLabel("test");
		lbl2.setBorder(null);
		lbl2.setBounds(602, 262, 96, 44);
		this.add(lbl2);
		
		cb2 = new JCheckBox("");
		cb2.setBackground(Color.WHITE);
		cb2.setBounds(555, 264, 31, 32);
		this.add(cb2);
		
		lbl3 = new JLabel("test");
		lbl3.setBorder(null);
		lbl3.setBounds(788, 260, 96, 44);
		this.add(lbl3);
		
		cb3 = new JCheckBox("");
		cb3.setBackground(Color.WHITE);
		cb3.setBounds(741, 262, 31, 32);
		this.add(cb3);
		
		lbl6 = new JLabel("test");
		lbl6.setBorder(null);
		lbl6.setBounds(788, 365, 90, 44);
		this.add(lbl6);
		
		cb6 = new JCheckBox("");
		cb6.setBackground(Color.WHITE);
		cb6.setBounds(741, 367, 31, 32);
		this.add(cb6);
		
		lbl5 = new JLabel("test");
		lbl5.setBorder(null);
		lbl5.setBounds(602, 367, 96, 44);
		this.add(lbl5);
		
		cb5 = new JCheckBox("");
		cb5.setBackground(Color.WHITE);
		cb5.setBounds(555, 369, 31, 32);
		this.add(cb5);
		
		lbl4 = new JLabel("test");
		lbl4.setBorder(null);
		lbl4.setBounds(415, 365, 96, 44);
		this.add(lbl4);
		
		cb4 = new JCheckBox("");
		cb4.setBackground(Color.WHITE);
		cb4.setBounds(368, 367, 31, 32);
		this.add(cb4);
		
		btnFinishRegistration = new JButton("Finish registration");
		btnFinishRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preparePaymentList();
				registerProperty(user, address, property, room, images, paymentList);
			}
		});
		btnFinishRegistration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnFinishRegistration.setBounds(421, 525, 408, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnFinishRegistration.setBounds(423, 527, 404, 55);
			}
		});
		btnFinishRegistration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFinishRegistration.setForeground(Color.WHITE);
		btnFinishRegistration.setFont(new Font("Dialog", Font.BOLD, 20));
		btnFinishRegistration.setBorder(null);
		btnFinishRegistration.setBackground(new Color(255, 88, 93));
		btnFinishRegistration.setBounds(423, 527, 404, 55);
		this.add(btnFinishRegistration);
		
		returnPaymentImagesFromDB();
		addLabelsToList();
		fillLabels();
	}
	
	private void registerProperty(User user, Address address, Property property, Map<RoomType, RoomInfo> room, List<File> images, List<PaymentType> paymentList) {
		PropertyWrapper propertyWrapper = new PropertyWrapper(user, address, property, room, images, paymentList);
		try {
			TransferClass transferClass = ControllerUI.getController().saveProperty(propertyWrapper);
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void preparePaymentList() {
		paymentList = new ArrayList<>();
		if (cb1.isSelected())
			paymentList.add(map.get(0));
		if (cb2.isSelected())
			paymentList.add(map.get(1));
		if (cb3.isSelected())
			paymentList.add(map.get(2));
		if (cb4.isSelected())
			paymentList.add(map.get(3));
		if (cb5.isSelected())
			paymentList.add(map.get(4));
		if (cb6.isSelected())
			paymentList.add(map.get(5));
	}
	private void fillLabels() {
		int index = 0;
		for (PaymentType py : paymentList) {
			String file = py.getImage();
			ImageIcon myImage = new ImageIcon(file);
			Image img = myImage.getImage();
			ImageIcon image = new ImageIcon(img);
			map.put(index, py);
			labelList.get(index++).setIcon(image);
		}
	}

	
	private void addLabelsToList() {
		labelList.add(lbl1);
		labelList.add(lbl2);
		labelList.add(lbl3);
		labelList.add(lbl4);
		labelList.add(lbl5);
		labelList.add(lbl6);
	}

	@SuppressWarnings("unchecked")
	private void returnPaymentImagesFromDB() {
		try {
			TransferClass transferClass = ControllerUI.getController().returnPaymentList();
			paymentList = (List<PaymentType>) transferClass.getServerResponse();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
