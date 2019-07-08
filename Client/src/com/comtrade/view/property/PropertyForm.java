package com.comtrade.view.property;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.Country;
import com.comtrade.domain.User;
import com.comtrade.transfer.TransferClass;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JLayeredPane;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PropertyForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;
	private JLayeredPane layeredPane;
	//--- navigation labels and panel
	private JPanel navigationPanel = new JPanel();
	private JLabel lblAddress;
	private JLabel lblPropertyInfo;
	private JLabel lblRoomtype;
	private JLabel lblRoomsInfo;
	private JLabel lblPropertyImages;
	private JLabel lblPayment;
	//----
	private JLabel lblCountryImage;
	private JComboBox<String> comboCountries;
	private List<Country> countries;
	private int idCountry;
	private JTextField tfStreet;
	private JTextField tfNumber;
	private JTextField tfCity;
	private JTextField tfZip;
	private JPanel BasicInfoPanel;
	private JLabel lblTest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropertyForm frame = new PropertyForm(null);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PropertyForm(User user) {
		this.user = user;
		initializeComponents();
	}

	private void initializeComponents() {
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
		
		JPanel AddressPanel = new JPanel();
		AddressPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(AddressPanel, "name_96510051729800");
		AddressPanel.setLayout(null);
		
		comboCountries = new JComboBox<>();
		comboCountries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String country = String.valueOf(comboCountries.getSelectedItem());
				setCountryImage(country);
			}
		});
		comboCountries.setFont(new Font("Dialog", Font.BOLD, 17));
		comboCountries.setBounds(481, 119, 389, 56);
		AddressPanel.add(comboCountries);
		
		lblCountryImage = new JLabel("");
		lblCountryImage.setBorder(null);
		lblCountryImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountryImage.setBounds(897, 119, 122, 56);
		AddressPanel.add(lblCountryImage);
		
		tfStreet = new JTextField();
		tfStreet.setForeground(new Color(71, 71, 71));
		tfStreet.setFont(new Font("Dialog", Font.BOLD, 19));
		tfStreet.setColumns(10);
		tfStreet.setBounds(487, 212, 383, 55);
		AddressPanel.add(tfStreet);
		
		JLabel lblStreet = new JLabel("Street:");
		lblStreet.setForeground(new Color(71, 71, 71));
		lblStreet.setFont(new Font("Dialog", Font.BOLD, 20));
		lblStreet.setBounds(242, 223, 124, 37);
		AddressPanel.add(lblStreet);
		
		tfNumber = new JTextField();
		tfNumber.setForeground(new Color(71, 71, 71));
		tfNumber.setFont(new Font("Dialog", Font.BOLD, 19));
		tfNumber.setColumns(10);
		tfNumber.setBounds(487, 297, 383, 55);
		AddressPanel.add(tfNumber);
		
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setForeground(new Color(71, 71, 71));
		lblNumber.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNumber.setBounds(242, 308, 124, 37);
		AddressPanel.add(lblNumber);
		
		tfCity = new JTextField();
		tfCity.setForeground(new Color(71, 71, 71));
		tfCity.setFont(new Font("Dialog", Font.BOLD, 19));
		tfCity.setColumns(10);
		tfCity.setBounds(487, 385, 383, 55);
		AddressPanel.add(tfCity);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setForeground(new Color(71, 71, 71));
		lblCity.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCity.setBounds(242, 396, 124, 37);
		AddressPanel.add(lblCity);
		
		tfZip = new JTextField();
		tfZip.setForeground(new Color(71, 71, 71));
		tfZip.setFont(new Font("Dialog", Font.BOLD, 19));
		tfZip.setColumns(10);
		tfZip.setBounds(487, 471, 383, 55);
		AddressPanel.add(tfZip);
		
		JLabel lblZippostalCode = new JLabel("Zip/Postal code:");
		lblZippostalCode.setForeground(new Color(71, 71, 71));
		lblZippostalCode.setFont(new Font("Dialog", Font.BOLD, 20));
		lblZippostalCode.setBounds(242, 482, 162, 37);
		AddressPanel.add(lblZippostalCode);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setForeground(new Color(71, 71, 71));
		lblCountry.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCountry.setBounds(242, 130, 124, 37);
		AddressPanel.add(lblCountry);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String street = tfStreet.getText();
				int num = Integer.parseInt(tfNumber.getText());
				String city = tfCity.getText();
				int zip = Integer.parseInt(tfZip.getText());
				
				updateUI(lblAddress, lblPropertyInfo);
				switchPannel(BasicInfoPanel);
			}
		});
		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnContinue.setBounds(240, 587, 632, 60);
			}
			                                                         
			@Override 
			public void mouseExited(MouseEvent e) {                
				btnContinue.setBounds(242, 589, 628, 55);
			}
		});
		btnContinue.setForeground(new Color(255, 255, 255));
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 20));
		btnContinue.setBorder(null);
		btnContinue.setBackground(new Color(255, 88, 93));
		btnContinue.setBounds(242, 589, 628, 55);
		AddressPanel.add(btnContinue);
		
		BasicInfoPanel = new JPanel();
		BasicInfoPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(BasicInfoPanel, "name_100717019548000");
		BasicInfoPanel.setLayout(null);
		
		lblTest = new JLabel("TestLabel");
		lblTest.setBounds(300, 373, 56, 16);
		BasicInfoPanel.add(lblTest);
		
		uploadCountryImagesFromDB();
	}
	
	protected void switchPannel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	protected void updateUI(JLabel label1, JLabel label2) {
		label1.setBorder(null);
		label2.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));// TODO Auto-generated method stub
	}

	// This methods will be removed 
	private void fillComboCountires() {
		for (Country country : countries) {
			comboCountries.addItem(country.getName());
		}
		idCountry = countries.get(0).getIdCountry();
		Image im = new ImageIcon(countries.get(0).getImage()).getImage().getScaledInstance(70, 35, Image.SCALE_DEFAULT);
		ImageIcon imIcon = new ImageIcon(im);
		lblCountryImage.setIcon(imIcon);
	}
	
	@SuppressWarnings("unchecked")
	private void uploadCountryImagesFromDB() {
		TransferClass transferClass = null;
		try {
			transferClass = ControllerUI.getController().returnCountriesList();
			countries = (List<Country>) transferClass.getServerResponse();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		fillComboCountires();
	}
	
	private void setCountryImage(String countryName) {
		for (Country country : countries) {
			if (country.getName().equals(countryName)) {
				idCountry = country.getIdCountry();
				File file = new File(country.getImage());
				Image im = new ImageIcon(file.toString()).getImage().getScaledInstance(70, 35, Image.SCALE_DEFAULT);
				ImageIcon imIcon = new ImageIcon(im);
				lblCountryImage.setIcon(imIcon);
				break;
			}
		}
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
