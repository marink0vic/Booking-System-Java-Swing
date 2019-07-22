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
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.comtrade.domain.Address;
import com.comtrade.domain.Country;

public class AddressPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JLabel lblCountryImage;
	private JComboBox<String> comboCountries;
	private int idCountry;
	private JTextField tfStreet;
	private JTextField tfNumber;
	private JTextField tfCity;
	private JTextField tfZip;
	//-------
	private List<Country> countries;
	private JLayeredPane layeredPane;
	private BasicInfoPanel basicInfoPanel;
	private JLabel lblPropertyInfo;
	private Address address;
	private Country propertyCountry;
	
	
	public AddressPanel(JLayeredPane layeredPane, BasicInfoPanel basicInfoPanel, JLabel lblPropertyInfo, Address address, List<Country> countries) {
		this.layeredPane = layeredPane;
		this.basicInfoPanel = basicInfoPanel;
		this.lblPropertyInfo = lblPropertyInfo;
		this.address = address;
		this.countries = countries;
		initializeComponents();
	}
	
	public Country getCountry() {
		return propertyCountry;
	}


	private void initializeComponents() {
		this.setBounds(0, 120, 1282, 783);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		comboCountries = new JComboBox<>();
		comboCountries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String country = String.valueOf(comboCountries.getSelectedItem());
				setCountryImage(country);
			}
		});
		comboCountries.setFont(new Font("Dialog", Font.BOLD, 17));
		comboCountries.setBounds(481, 119, 389, 56);
		this.add(comboCountries);
		
		lblCountryImage = new JLabel("");
		lblCountryImage.setBorder(null);
		lblCountryImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountryImage.setBounds(897, 119, 122, 56);
		this.add(lblCountryImage);
		
		tfStreet = new JTextField();
		tfStreet.setForeground(new Color(71, 71, 71));
		tfStreet.setFont(new Font("Dialog", Font.BOLD, 19));
		tfStreet.setColumns(10);
		tfStreet.setBounds(481, 212, 389, 55);
		this.add(tfStreet);
		
		JLabel lblStreet = new JLabel("Street:");
		lblStreet.setForeground(new Color(71, 71, 71));
		lblStreet.setFont(new Font("Dialog", Font.BOLD, 20));
		lblStreet.setBounds(242, 223, 124, 37);
		this.add(lblStreet);
		
		tfNumber = new JTextField();
		tfNumber.setForeground(new Color(71, 71, 71));
		tfNumber.setFont(new Font("Dialog", Font.BOLD, 19));
		tfNumber.setColumns(10);
		tfNumber.setBounds(481, 297, 389, 55);
		this.add(tfNumber);
		
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setForeground(new Color(71, 71, 71));
		lblNumber.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNumber.setBounds(242, 308, 124, 37);
		this.add(lblNumber);
		
		tfCity = new JTextField();
		tfCity.setForeground(new Color(71, 71, 71));
		tfCity.setFont(new Font("Dialog", Font.BOLD, 19));
		tfCity.setColumns(10);
		tfCity.setBounds(481, 385, 389, 55);
		this.add(tfCity);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setForeground(new Color(71, 71, 71));
		lblCity.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCity.setBounds(242, 396, 124, 37);
		this.add(lblCity);
		
		tfZip = new JTextField();
		tfZip.setForeground(new Color(71, 71, 71));
		tfZip.setFont(new Font("Dialog", Font.BOLD, 19));
		tfZip.setColumns(10);
		tfZip.setBounds(481, 471, 389, 55);
		this.add(tfZip);
		
		JLabel lblZippostalCode = new JLabel("Zip/Postal code:");
		lblZippostalCode.setForeground(new Color(71, 71, 71));
		lblZippostalCode.setFont(new Font("Dialog", Font.BOLD, 20));
		lblZippostalCode.setBounds(242, 482, 162, 37);
		this.add(lblZippostalCode);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setForeground(new Color(71, 71, 71));
		lblCountry.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCountry.setBounds(242, 130, 124, 37);
		this.add(lblCountry);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAddress();
				updateUI(lblPropertyInfo);
				switchPanel(basicInfoPanel);
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
		this.add(btnContinue);
		
		fillComboCountires();
	}
	
	private void createAddress() {
		String street = tfStreet.getText();
		int num = Integer.parseInt(tfNumber.getText());
		String city = tfCity.getText();
		int zip = Integer.parseInt(tfZip.getText());
		
		address.setIdCountry(idCountry);
		address.setStreet(street);
		address.setNumber(num);
		address.setCity(city);
		address.setZipCode(zip);
	}


	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void updateUI(JLabel label) {
		label.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
	}
	
	private void fillComboCountires() {
		for (Country country : countries) {
			comboCountries.addItem(country.getName());
		}
		idCountry = countries.get(0).getIdCountry();
		Image im = new ImageIcon(countries.get(0).getImage()).getImage().getScaledInstance(62, 35, Image.SCALE_DEFAULT);
		ImageIcon imIcon = new ImageIcon(im);
		lblCountryImage.setIcon(imIcon);
	}

	private void setCountryImage(String countryName) {
		for (Country country : countries) {
			if (country.getName().equals(countryName)) {
				idCountry = country.getIdCountry();
				propertyCountry = country;
				
				File file = new File(country.getImage());
				Image im = new ImageIcon(file.toString()).getImage().getScaledInstance(62, 35, Image.SCALE_DEFAULT);
				ImageIcon imIcon = new ImageIcon(im);
				lblCountryImage.setIcon(imIcon);
				break;
			}
		}
	}

}
