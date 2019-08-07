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
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.comtrade.constants.CityLocations;
import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.Location;
import com.comtrade.domain.Country;
import javax.swing.border.LineBorder;

public class AddressPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JLabel lblCountryImage;
	private JComboBox<String> comboCountries;
	private int idCountry;
	private JTextField tfStreet;
	private JTextField tfNumber;
	private JTextField tfZip;
	//-------
	private JLayeredPane layeredPane;
	private BasicInfoPanel basicInfoPanel;
	private List<Country> countries;
	private String countryName;
	private JComboBox<String> comboCities;
	private JLabel lblPropertyInfo;
	private Location address;
	private Country propertyCountry;
	
	
	public AddressPanel(JLayeredPane layeredPane, BasicInfoPanel basicInfoPanel, JLabel lblPropertyInfo, Location address, List<Country> countries) {
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
				countryName = String.valueOf(comboCountries.getSelectedItem());
				setCountryImage(countryName);
				fillComboCities();
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
		
		comboCities = new JComboBox<>();
		comboCities.setFont(new Font("Dialog", Font.BOLD, 17));
		comboCities.setBounds(481, 210, 389, 56);
		add(comboCities);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setForeground(new Color(71, 71, 71));
		lblCity.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCity.setBounds(242, 221, 124, 37);
		add(lblCity);
		
		tfStreet = new JTextField();
		tfStreet.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfStreet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tfStreet.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
			}
		});
		tfStreet.setForeground(new Color(71, 71, 71));
		tfStreet.setFont(new Font("Dialog", Font.BOLD, 19));
		tfStreet.setColumns(10);
		tfStreet.setBounds(481, 297, 389, 55);
		this.add(tfStreet);
		
		JLabel lblStreet = new JLabel("Street:");
		lblStreet.setForeground(new Color(71, 71, 71));
		lblStreet.setFont(new Font("Dialog", Font.BOLD, 20));
		lblStreet.setBounds(242, 308, 124, 37);
		this.add(lblStreet);
		
		tfNumber = new JTextField();
		tfNumber.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfNumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tfNumber.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
			}
		});
		tfNumber.setForeground(new Color(71, 71, 71));
		tfNumber.setFont(new Font("Dialog", Font.BOLD, 19));
		tfNumber.setColumns(10);
		tfNumber.setBounds(481, 382, 389, 55);
		this.add(tfNumber);
		
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setForeground(new Color(71, 71, 71));
		lblNumber.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNumber.setBounds(242, 393, 124, 37);
		this.add(lblNumber);
		
		tfZip = new JTextField();
		tfZip.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfZip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tfZip.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
			}
		});
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
				if (checkFields()) {
					createAddress();
					updateUI(lblPropertyInfo);
					switchPanel(basicInfoPanel);
				} else {
					JOptionPane.showMessageDialog(null, "You can't leave empty fields");
				}
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
		fillComboCities();
	}

	protected boolean checkFields() {
		int counter = 0;
		if (tfStreet.getText().length() == 0) {
			tfStreet.setBorder(new LineBorder(ColorConstants.RED));
			counter ++;
		}
		if (tfNumber.getText().length() == 0) {
			tfNumber.setBorder(new LineBorder(ColorConstants.RED));
			counter++;
		}
		if (tfZip.getText().length() == 0) {
			tfZip.setBorder(new LineBorder(ColorConstants.RED));
			counter++;
		}
		return counter == 0;
	}

	private void createAddress() {
		String street = tfStreet.getText();
		String num = tfNumber.getText();
		String city = (String) comboCities.getSelectedItem();
		int zip = Integer.parseInt(tfZip.getText());
		double[] coordinates = getCoordinates();
		
		address.setIdCountry(idCountry);
		address.setStreet(street);
		address.setNumber(num);
		address.setCity(city);
		address.setZipCode(zip);
		address.setLatitude(coordinates[0]);
		address.setLongitude(coordinates[1]);
	}
	
	private double[] getCoordinates() {
		double[] coordinates = new double[2];
		File f = fileToLoop(countryName);
		Scanner scanner = null;
		try {
			scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] array = line.split(";");
				if ((array[1].equals(comboCities.getSelectedItem()))) {
					coordinates[0] = Double.parseDouble(array[2]);
					coordinates[1] = Double.parseDouble(array[3]);
					break;
				} 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return coordinates;
	}

	private void fillComboCountires() {
		for (Country country : countries) {
			comboCountries.addItem(country.getName());
		}
		countryName = countries.get(0).getName();
		idCountry = countries.get(0).getIdCountry();
		Image im = new ImageIcon(countries.get(0).getImage()).getImage().getScaledInstance(62, 35, Image.SCALE_DEFAULT);
		ImageIcon imIcon = new ImageIcon(im);
		lblCountryImage.setIcon(imIcon);
	}
	
	private void fillComboCities() {
		comboCities.removeAllItems();
		File f = fileToLoop(countryName);
		boolean flag = false;
		Scanner scanner = null;
		try {
			scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] array = line.split(";");
				if (!(array[0].equals(countryName)) && flag) {
					break;
				} else {
					if (array[0].equals(countryName)) {
						flag = true;
						comboCities.addItem(array[1]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private File fileToLoop(String country_name) {
		File f = null;
		int c = country_name.charAt(0);
		if (c > 80) {
			f = new File(CityLocations.FROM_Q_TO_V);
		} else if (c > 74) {
			f = new File(CityLocations.FROM_K_TO_P);
		} else if (c > 67) {
			f = new File(CityLocations.FROM_D_TO_J);
		} else {
			f = new File(CityLocations.FROM_A_TO_C);
		}
		return f;
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
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void updateUI(JLabel label) {
		label.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
	}
}
