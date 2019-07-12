package com.comtrade.view.property;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.domain.Address;
import com.comtrade.domain.Country;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLayeredPane;

import java.awt.CardLayout;


public class PropertyForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;
	private Address address;
	private Property property;
	private JLayeredPane layeredPane;
	private List<RoomType> listOfTypes;
	private Map<RoomType, RoomInfo> room;
	private List<PropertyImage> propertyImageFiles;
	private List<Country> countries;
	
	//--- navigation labels and panel
	private JPanel navigationPanel = new JPanel();
	private JLabel lblAddress;
	private JLabel lblPropertyInfo;
	private JLabel lblRoomtype;
	private JLabel lblRoomsInfo;
	private JLabel lblPropertyImages;
	private JLabel lblPayment;
	
	//---- layered panels
	private AddressPanel addressPanel;
	private BasicInfoPanel basicInfoPanel;
	private RoomTypePanel roomTypePanel;
	private RoomPanel roomPanel;
	private ImagesPanel imagesPanel;
	private PaymentPanel paymentPanel;
	

	/**
	 * Create the frame.
	 */
	public PropertyForm(User user, List<Country> countries) {
		this.user = user;
		this.countries = countries;
		initializeComponents();
	}

	private void initializeComponents() {
		listOfTypes = new ArrayList<>();
		room = new LinkedHashMap<>();
		propertyImageFiles = new ArrayList<>();
		address = new Address();
		property = new Property();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		createNavigationPanel();
		
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 120, 1282, 783);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		//--- panels
		paymentPanel = new PaymentPanel(user, address, property, room, propertyImageFiles);
		imagesPanel = new ImagesPanel(layeredPane, propertyImageFiles, paymentPanel, lblPropertyImages, lblPayment);
		roomPanel = new RoomPanel(layeredPane, listOfTypes, room, lblRoomsInfo, lblPropertyImages, imagesPanel);
		roomTypePanel = new RoomTypePanel(layeredPane, listOfTypes, roomPanel, lblRoomtype, lblRoomsInfo);
		basicInfoPanel = new BasicInfoPanel(layeredPane, roomTypePanel, lblPropertyInfo, lblRoomtype);
		addressPanel = new AddressPanel(layeredPane, basicInfoPanel,lblAddress,lblPropertyInfo, address, countries);
		
		basicInfoPanel.setUser(user);
		basicInfoPanel.setProperty(property);
		paymentPanel.setPropertyForm(this);
		
		layeredPane.add(addressPanel, "name_96510051729800");
		layeredPane.add(basicInfoPanel, "name_100717019548000");
		layeredPane.add(roomTypePanel, "name_153876851081600");
		layeredPane.add(roomPanel, "name_237876643457800");
		layeredPane.add(imagesPanel, "name_241975292003400");
		layeredPane.add(paymentPanel, "name_258197792870600");

	}
	
	
	private void createNavigationPanel() {
		navigationPanel.setBackground(new Color(71, 71, 71));
		navigationPanel.setBounds(0, 0, 1300, 121);
		contentPane.add(navigationPanel);
		navigationPanel.setLayout(null);
		
		lblAddress = new JLabel("Adress");
		lblAddress.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAddress.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setForeground(new Color(255, 255, 255));
		lblAddress.setFont(new Font("Dialog", Font.BOLD, 18));
		lblAddress.setBounds(57, 36, 158, 59);
		navigationPanel.add(lblAddress);
		
		lblPropertyInfo = new JLabel("Property info");
		lblPropertyInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyInfo.setForeground(Color.WHITE);
		lblPropertyInfo.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPropertyInfo.setBorder(null);
		lblPropertyInfo.setBounds(241, 35, 172, 60);
		navigationPanel.add(lblPropertyInfo);
		
		lblRoomtype = new JLabel("RoomType");
		lblRoomtype.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomtype.setForeground(Color.WHITE);
		lblRoomtype.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRoomtype.setBorder(null);
		lblRoomtype.setBounds(447, 35, 166, 60);
		navigationPanel.add(lblRoomtype);
		
		lblRoomsInfo = new JLabel("Rooms info");
		lblRoomsInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomsInfo.setForeground(Color.WHITE);
		lblRoomsInfo.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRoomsInfo.setBorder(null);
		lblRoomsInfo.setBounds(643, 35, 158, 60);
		navigationPanel.add(lblRoomsInfo);
		
		lblPropertyImages = new JLabel("Property images");
		lblPropertyImages.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyImages.setForeground(Color.WHITE);
		lblPropertyImages.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPropertyImages.setBorder(null);
		lblPropertyImages.setBounds(837, 35, 203, 60);
		navigationPanel.add(lblPropertyImages);
		
		lblPayment = new JLabel("Payment");
		lblPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblPayment.setForeground(Color.WHITE);
		lblPayment.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPayment.setBorder(null);
		lblPayment.setBounds(1070, 36, 144, 59);
		navigationPanel.add(lblPayment);
	}
	
	
}
