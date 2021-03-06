package com.comtrade.view.property;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.Location;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.user.host.PropertyOwnerFrame;

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
	private PropertyForm propertyForm;
	
	private User user;
	private Location address;
	private Property property;
	private Map<RoomType, Room> room;
	private List<PropertyImage> images; 
	private List<PaymentType> paymentList;
	private Country country;

	
	public PaymentPanel(User user, Location address, Property property, Map<RoomType, Room> room, List<PropertyImage> images) {
		this.user = user;
		this.address = address;
		this.property = property;
		this.room = room;
		this.images = images;
		initializeComponents();
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}

	public void setPropertyForm(PropertyForm propertyForm) {
		this.propertyForm = propertyForm;
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
				if (paymentList.size() == 0) {
					JOptionPane.showMessageDialog(null, "You have to select at least one payment method");
				} else {
					registerProperty();
					openPropertyOwnerFrame();
				}
			}
		});
		btnFinishRegistration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFinishRegistration.setForeground(Color.WHITE);
		btnFinishRegistration.setFont(new Font("Dialog", Font.BOLD, 20));
		btnFinishRegistration.setBackground(new Color(255, 88, 93));
		btnFinishRegistration.setBounds(423, 527, 404, 55);
		this.add(btnFinishRegistration);
		
		returnPaymentImagesFromDB();
		addLabelsToList();
		fillLabels();
	}

	private void registerProperty() {
		PropertyWrapper propertyWrapper = new PropertyWrapper(user, address, property, room, images, paymentList);
		propertyWrapper.setCountry(country);
		TransferClass transfer = new TransferClass();
		transfer.setClientRequest(propertyWrapper);
		transfer.setOperation(Operations.SAVE_ALL_PROPERTY_INFO);
		transfer.setDomainType(DomainType.PROPERTY);
		ControllerUI.getController().sendToServer(transfer);
	}

	private void openPropertyOwnerFrame() {
		PropertyWrapper returnedProperty = ControllerUI.getController().getPropertyWrapper();
		PropertyOwnerFrame propertyOwner = new PropertyOwnerFrame(returnedProperty);
		propertyOwner.setLocationRelativeTo(null);
		propertyOwner.setVisible(true);
		propertyForm.dispose();
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

	private void returnPaymentImagesFromDB() {
		TransferClass transferClass = new TransferClass();
		transferClass.setDomainType(DomainType.PAYMENT_TYPE);
		transferClass.setOperation(Operations.RETURN_ALL);
		ControllerUI.getController().sendToServer(transferClass);
		
		paymentList = ControllerUI.getController().getPayments();
	}

}
