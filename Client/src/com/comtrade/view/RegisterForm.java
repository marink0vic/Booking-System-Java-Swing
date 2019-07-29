package com.comtrade.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.Country;
import com.comtrade.domain.User;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.property.PropertyForm;
import com.comtrade.view.user.regular.UserHomeFrame;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final String STATUS;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JTextField tfEmail;
	private JTextField tfUsername;
	private JPasswordField passwordField;
	private JComboBox<Integer> comboYear;
	private JComboBox<Month> comboMonth;
	private JComboBox<Integer> comboDay;
	private JComboBox<String> comboCountries;
	private List<Country> countries;
	private Country userCountry;
	private int idCountry;
	private int year;
	private Month month;
	private int day;
	private JLabel lblImage;

	/**
	 * Create the frame.
	 */
	public RegisterForm(String STATUS) {
		this.STATUS = STATUS;
		userCountry = new Country();
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 950);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(71, 71, 71));
		panel.setBounds(0, 0, 632, 101);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SIGN UP FORM");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(209, 31, 205, 47);
		panel.add(lblNewLabel);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setForeground(new Color(71, 71, 71));
		lblFirstName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblFirstName.setBounds(51, 156, 124, 37);
		contentPane.add(lblFirstName);
		
		tfFirstName = new JTextField();
		tfFirstName.setFont(new Font("Dialog", Font.BOLD, 19));
		tfFirstName.setForeground(new Color(71, 71, 71));
		tfFirstName.setBounds(198, 143, 383, 55);
		contentPane.add(tfFirstName);
		tfFirstName.setColumns(10);
		
		tfLastName = new JTextField();
		tfLastName.setForeground(new Color(71, 71, 71));
		tfLastName.setFont(new Font("Dialog", Font.BOLD, 19));
		tfLastName.setColumns(10);
		tfLastName.setBounds(198, 226, 383, 55);
		contentPane.add(tfLastName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setForeground(new Color(71, 71, 71));
		lblLastName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblLastName.setBounds(51, 239, 124, 37);
		contentPane.add(lblLastName);
		
		tfEmail = new JTextField();
		tfEmail.setForeground(new Color(71, 71, 71));
		tfEmail.setFont(new Font("Dialog", Font.BOLD, 19));
		tfEmail.setColumns(10);
		tfEmail.setBounds(198, 311, 383, 55);
		contentPane.add(tfEmail);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(71, 71, 71));
		lblEmail.setFont(new Font("Dialog", Font.BOLD, 20));
		lblEmail.setBounds(51, 324, 124, 37);
		contentPane.add(lblEmail);
		
		tfUsername = new JTextField();
		tfUsername.setForeground(new Color(71, 71, 71));
		tfUsername.setFont(new Font("Dialog", Font.BOLD, 19));
		tfUsername.setColumns(10);
		tfUsername.setBounds(198, 394, 383, 55);
		contentPane.add(tfUsername);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(new Color(71, 71, 71));
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 20));
		lblUsername.setBounds(51, 407, 124, 37);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(71, 71, 71));
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPassword.setBounds(51, 491, 124, 37);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(71, 71, 71));
		passwordField.setFont(new Font("Dialog", Font.BOLD, 13));
		passwordField.setBounds(198, 480, 383, 55);
		contentPane.add(passwordField);
		
		JLabel lblYourBirthday = new JLabel("Your birthday");
		lblYourBirthday.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourBirthday.setForeground(new Color(71, 71, 71));
		lblYourBirthday.setFont(new Font("Dialog", Font.BOLD, 20));
		lblYourBirthday.setBounds(214, 573, 192, 37);
		contentPane.add(lblYourBirthday);
		
		comboYear = new JComboBox<>();
		comboYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				year = Integer.parseInt(comboYear.getSelectedItem().toString());
			}
		});
		comboYear.setForeground(new Color(71, 71, 71));
		comboYear.setFont(new Font("Dialog", Font.BOLD, 17));
		comboYear.setBounds(51, 635, 149, 45);
		contentPane.add(comboYear);
		
		comboMonth = new JComboBox<>();
		comboMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				month = (Month) comboMonth.getSelectedItem();
			}
		});
		comboMonth.setForeground(new Color(71, 71, 71));
		comboMonth.setFont(new Font("Dialog", Font.BOLD, 17));
		comboMonth.setBounds(235, 635, 184, 45);
		contentPane.add(comboMonth);
		
		comboDay = new JComboBox<>();
		comboDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day = Integer.parseInt(comboDay.getSelectedItem().toString());
			}
		});
		comboDay.setForeground(new Color(71, 71, 71));
		comboDay.setFont(new Font("Dialog", Font.BOLD, 17));
		comboDay.setBounds(451, 635, 130, 45);
		contentPane.add(comboDay);
		
		comboCountries = new JComboBox<>();
		comboCountries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String country = String.valueOf(comboCountries.getSelectedItem());
				setCountryImage(country);
			}
		});
		comboCountries.setFont(new Font("Dialog", Font.BOLD, 17));
		comboCountries.setBounds(51, 733, 368, 45);
		contentPane.add(comboCountries);
		
		lblImage = new JLabel("");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(438, 733, 102, 45);
		contentPane.add(lblImage);
		
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = createUser();
				TransferClass transferClass = new TransferClass();
				transferClass.setClientRequest(user);
				transferClass.setDomainType(DomainType.USER);
				transferClass.setOperation(Operations.SAVE);
				ControllerUI.getController().sendToServer(transferClass);
				
				User savedUser = ControllerUI.getController().getUser();
				savedUser.setCountry(userCountry);
				
				if (user != null) {
					if (STATUS.equals("SUPER_USER")) {
						user.setCountry(userCountry);
						PropertyForm propertyForm = new PropertyForm(user, countries);
						propertyForm.setLocationRelativeTo(null);
						propertyForm.setVisible(true);
						dispose();
					} else {
						user.setCountry(userCountry);
						UserHomeFrame mainUserFrame = new UserHomeFrame(user);
						mainUserFrame.setLocationRelativeTo(null);
						mainUserFrame.setVisible(true);
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "<html>Something went wrong with your information"
							+ "<br> we'll get back to you soon</html>");
				}
			}
		});
		btnSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSignIn.setBounds(172, 822, 290, 60);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnSignIn.setBounds(174, 824, 285, 55);
			}
		});
		
		btnSignIn.setForeground(new Color(255, 255, 255));
		btnSignIn.setFont(new Font("Dialog", Font.BOLD, 20));
		btnSignIn.setBounds(174, 824, 285, 55);
		btnSignIn.setBackground(new Color(255, 88, 93));
		btnSignIn.setBorder(null);
		contentPane.add(btnSignIn);
		
		fillComboYear();
		fillComboMonth();
		fillComboDay();
		uploadCountryImagesFromDB();
	}

	protected User createUser() {
		String firstName = tfFirstName.getText();
		String lastName = tfLastName.getText();
		String email = tfEmail.getText();
		String username = tfUsername.getText();
		String password = String.copyValueOf(passwordField.getPassword());
		LocalDate dateOfBirth = LocalDate.of(year, month, day);
		return new User(userCountry, firstName, lastName, email, username, password, dateOfBirth, STATUS);
	}

	private void fillComboYear() {
		for (int i = Year.now().getValue(); i > 1899 ; i--) {
			comboYear.addItem(i);
		}
	}
	private void fillComboMonth() {
		Month[] months = Month.values();
		for (int i = 0; i < months.length; i++) {
			comboMonth.addItem(months[i]);
		}
	}
	private void fillComboDay() {
		for (int i = 1; i < 32; i++) {
			comboDay.addItem(i);
		}
	}
	
	private void fillComboCountires() {
		for (Country country : countries) {
			comboCountries.addItem(country.getName());
		}
		idCountry = countries.get(0).getIdCountry();
		ImageIcon im = new ImageIcon(countries.get(0).getImage());
		lblImage.setIcon(im);
	}
	
	private void uploadCountryImagesFromDB() {
		TransferClass transferClass = new TransferClass();
		transferClass.setDomainType(DomainType.COUNTRY);
		transferClass.setOperation(Operations.RETURN_ALL);
		ControllerUI.getController().sendToServer(transferClass);
		
		countries = ControllerUI.getController().getCountryImages();
		fillComboCountires();
	}
	
	private void setCountryImage(String countryName) {
		for (Country c : countries) {
			if (c.getName().equals(countryName)) {
				idCountry = c.getIdCountry();
				File file = new File(c.getImage());
				ImageIcon im = new ImageIcon(file.toString());
				lblImage.setIcon(im);
				
				userCountry.setIdCountry(idCountry);
				userCountry.setName(countryName);
				userCountry.setImage(c.getImage());
				break;
			}
		}
	}
}
