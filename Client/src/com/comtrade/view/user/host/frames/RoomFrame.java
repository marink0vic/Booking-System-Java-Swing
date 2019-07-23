package com.comtrade.view.user.host.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.Color;
import javax.swing.JComboBox;
import com.comtrade.constants.RoomTypeConstants;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.user.host.HomePanel;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfPrice;
	private List<RoomTypeConstants> constantsRoomTypes;
	private JComboBox<RoomTypeConstants> comboRoomType;
	private JSpinner spinnerRoom;
	private JSpinner spinnerBads;
	private JCheckBox cbKitchen;
	private JCheckBox cbTv;
	private JCheckBox cbWifi;
	private JCheckBox cbAir;
	
	private PropertyWrapper propertyOwner;
	private HomePanel panel;
	private String action;
	private RoomType roomType;
	private Room roomInfo;

	/**
	 * Create the frame.
	 */
	public RoomFrame(HomePanel panel, PropertyWrapper propertyOwner, String action) {
		this.panel = panel;
		this.propertyOwner = propertyOwner;
		constantsRoomTypes = new ArrayList<>();
		this.action = action;
		initializeComponents();
		
	}

	public RoomFrame(HomePanel homePanel, PropertyWrapper propertyOwner, RoomType roomType, Room roomInfo, String action) {
		this.panel = homePanel;
		this.propertyOwner = propertyOwner;
		this.roomType = roomType;
		this.roomInfo = roomInfo;
		this.action = action;
		constantsRoomTypes = new ArrayList<>();
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1282, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblType = new JLabel("Type of room:");
		lblType.setForeground(new Color(71, 71, 71));
		lblType.setFont(new Font("Dialog", Font.BOLD, 20));
		lblType.setBounds(89, 107, 174, 37);
		contentPane.add(lblType);
		
		comboRoomType = new JComboBox<>();
		comboRoomType.setForeground(new Color(71, 71, 71));
		comboRoomType.setFont(new Font("Dialog", Font.BOLD, 17));
		comboRoomType.setBounds(412, 96, 299, 56);
		contentPane.add(comboRoomType);
		
		JLabel lblNumberOfRooms = new JLabel("Number of room(of this type)");
		lblNumberOfRooms.setForeground(new Color(71, 71, 71));
		lblNumberOfRooms.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNumberOfRooms.setBounds(89, 193, 291, 37);
		contentPane.add(lblNumberOfRooms);
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 100, 1);
		spinnerRoom = new JSpinner(sm);
		spinnerRoom.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinnerRoom.setBounds(412, 181, 62, 49);
		contentPane.add(spinnerRoom);
		
		JLabel lblPrice = new JLabel("Price per night:");
		lblPrice.setForeground(new Color(71, 71, 71));
		lblPrice.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPrice.setBounds(89, 271, 155, 37);
		contentPane.add(lblPrice);
		
		tfPrice = new JTextField();
		tfPrice.setForeground(new Color(71, 71, 71));
		tfPrice.setFont(new Font("Dialog", Font.BOLD, 19));
		tfPrice.setColumns(10);
		tfPrice.setBounds(412, 262, 299, 55);
		contentPane.add(tfPrice);
		
		JButton btnAction = new JButton("");
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RoomType type = createRoomType();
				Room info = createRoomInfo();
				PropertyWrapper tempOwner = new PropertyWrapper();
				Map<RoomType, Room> room = new HashMap<>();
				TransferClass transferClass = new TransferClass();
				
				if (action.equals("ADD")) {
					room.put(type, info);
					tempOwner.setRooms(room);
					addNewRoom(transferClass, tempOwner);
				} else if (action.equals("UPDATE")) {
					updateRoomType();
					updateRoomInfo();
					room.put(roomType, roomInfo);
					tempOwner.setRooms(room);
					updateRoom(transferClass, tempOwner);
				}
				
				panel.fillRoomTypeTable();
				dispose();
			}
		});
		btnAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAction.setBounds(489, 428, 285, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAction.setBounds(491, 430, 281, 55);
			}
		});
		btnAction.setForeground(Color.WHITE);
		btnAction.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAction.setBorder(null);
		btnAction.setBackground(new Color(255, 88, 93));
		btnAction.setBounds(491, 430, 281, 55);
		if (action.equals("ADD")) btnAction.setText("Add new room");
		if (action.equals("UPDATE")) btnAction.setText("Update room");
		contentPane.add(btnAction);
		
		JLabel lblNumOfBads = new JLabel("Number of bads");
		lblNumOfBads.setForeground(new Color(71, 71, 71));
		lblNumOfBads.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNumOfBads.setBounds(788, 99, 183, 37);
		contentPane.add(lblNumOfBads);
		
		SpinnerModel sm1 = new SpinnerNumberModel(1, 1, 10, 1);
		spinnerBads = new JSpinner(sm1);
		spinnerBads.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinnerBads.setBounds(983, 96, 64, 45);
		contentPane.add(spinnerBads);
		
		cbKitchen = new JCheckBox(" Kitchen");
		cbKitchen.setForeground(new Color(71, 71, 71));
		cbKitchen.setFont(new Font("Dialog", Font.BOLD, 17));
		cbKitchen.setBackground(Color.WHITE);
		cbKitchen.setBounds(788, 191, 113, 25);
		contentPane.add(cbKitchen);
		
		cbWifi = new JCheckBox(" Wifi");
		cbWifi.setForeground(new Color(71, 71, 71));
		cbWifi.setFont(new Font("Dialog", Font.BOLD, 17));
		cbWifi.setBackground(Color.WHITE);
		cbWifi.setBounds(941, 193, 113, 25);
		contentPane.add(cbWifi);
		
		cbTv = new JCheckBox(" TV");
		cbTv.setForeground(new Color(71, 71, 71));
		cbTv.setFont(new Font("Dialog", Font.BOLD, 17));
		cbTv.setBackground(Color.WHITE);
		cbTv.setBounds(788, 252, 113, 25);
		contentPane.add(cbTv);
		
		cbAir = new JCheckBox(" Air conditioning");
		cbAir.setForeground(new Color(71, 71, 71));
		cbAir.setFont(new Font("Dialog", Font.BOLD, 17));
		cbAir.setBackground(Color.WHITE);
		cbAir.setBounds(941, 252, 174, 25);
		contentPane.add(cbAir);
		
		fillConstantsList();
		fillCombo();
		if (action.equals("UPDATE")) fiilInputWithWalues();
	}

	private void updateRoom(TransferClass transferClass, PropertyWrapper tempOwner) {
		try {
			transferClass = ControllerUI.getController().updateRoom(tempOwner);
			System.out.println(transferClass.getMessageResponse());
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
	}

	private void addNewRoom(TransferClass transferClass, PropertyWrapper tempOwner) {
		try {
			transferClass = ControllerUI.getController().saveRoom(tempOwner);
			tempOwner = (PropertyWrapper) transferClass.getServerResponse();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		propertyOwner.getRooms().putAll(tempOwner.getRooms());
	}

	private void fiilInputWithWalues() {
		comboRoomType.addItem(RoomTypeConstants.valueOf(roomType.getRoomType()));
		spinnerRoom.setValue(roomType.getNumberOfRooms());
		tfPrice.setText(String.valueOf(roomType.getPricePerNight()));
		
		spinnerBads.setValue(roomInfo.getNumOfBads());
		if (roomInfo.isKitchen())  cbKitchen.setSelected(true);
		if (roomInfo.isTv()) cbTv.setSelected(true);
		if (roomInfo.isAirConditioning()) cbAir.setSelected(true);
		if (roomInfo.isWifi()) cbWifi.setSelected(true);
	}

	private void fillConstantsList() {
		constantsRoomTypes.add(RoomTypeConstants.STANDARD);
		constantsRoomTypes.add(RoomTypeConstants.FAMILY);
		constantsRoomTypes.add(RoomTypeConstants.STUDIO);
		constantsRoomTypes.add(RoomTypeConstants.DELUXE);
		constantsRoomTypes.add(RoomTypeConstants.APARTMENT);
	}
	
	private void fillCombo() {
		for (RoomTypeConstants constant : constantsRoomTypes) {
			if (!containsRoom(constant.name())) {
				comboRoomType.addItem(constant);
			}
		}
	}

	private boolean containsRoom(String name) {
		for (Entry<RoomType, Room> map : propertyOwner.getRooms().entrySet()) {
			String roomTypeName = map.getKey().getRoomType();
			if (roomTypeName.equals(name)) return true;
		}
		return false;
	}
	
	private RoomType createRoomType() {
		String typeOfRoom = String.valueOf(comboRoomType.getSelectedItem());
		int numOfRooms = (Integer) spinnerRoom.getValue();
		Double pricePerNight = Double.valueOf(tfPrice.getText());
		int idProperty = propertyOwner.getProperty().getIdProperty();
		RoomType type = new RoomType(typeOfRoom, numOfRooms, pricePerNight);
		type.setIdProperty(idProperty);
		return type;
	}
	
	private void updateRoomType() {
		RoomType temp = createRoomType();
		roomType.setRoomType(temp.getRoomType());
		roomType.setNumberOfRooms(temp.getNumberOfRooms());
		roomType.setPricePerNight(temp.getPricePerNight());
	}
	
	private Room createRoomInfo() {
		int numOfBads = (Integer) spinnerBads.getValue();
		boolean kitchen = cbKitchen.isSelected();
		boolean tv = cbTv.isSelected();
		boolean airConditioning = cbAir.isSelected();
		boolean wifi = cbWifi.isSelected();
		return new Room(numOfBads, kitchen, tv, airConditioning, wifi);
	}
	
	private void updateRoomInfo() {
		Room temp = createRoomInfo();
		roomInfo.setNumOfBads(temp.getNumOfBads());
		roomInfo.setKitchen(temp.isKitchen());
		roomInfo.setTv(temp.isTv());
		roomInfo.setAirConditioning(temp.isAirConditioning());
		roomInfo.setWifi(temp.isWifi());
	}
	
}
