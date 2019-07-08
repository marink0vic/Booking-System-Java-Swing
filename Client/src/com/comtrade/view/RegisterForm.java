package com.comtrade.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.time.Month;
import java.time.Year;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final String USER;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JTextField tfEmail;
	private JTextField tfUsername;
	private JPasswordField passwordField;
	private JComboBox<Integer> comboYear;
	private JComboBox<Month> comboMonth;
	private JComboBox<Integer> comboDay;
	private JComboBox<String> comboCountries;
	private JLabel lblImage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterForm frame = new RegisterForm("");
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
	public RegisterForm(String USER) {
		this.USER = USER;
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
		passwordField.setBounds(198, 480, 383, 55);
		contentPane.add(passwordField);
		
		JLabel lblYourBirthday = new JLabel("Your birthday");
		lblYourBirthday.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourBirthday.setForeground(new Color(71, 71, 71));
		lblYourBirthday.setFont(new Font("Dialog", Font.BOLD, 20));
		lblYourBirthday.setBounds(214, 573, 192, 37);
		contentPane.add(lblYourBirthday);
		
		comboYear = new JComboBox<>();
		comboYear.setForeground(new Color(71, 71, 71));
		comboYear.setFont(new Font("Dialog", Font.BOLD, 17));
		comboYear.setBounds(51, 635, 149, 45);
		contentPane.add(comboYear);
		
		comboMonth = new JComboBox<>();
		comboMonth.setForeground(new Color(71, 71, 71));
		comboMonth.setFont(new Font("Dialog", Font.BOLD, 17));
		comboMonth.setBounds(235, 635, 184, 45);
		contentPane.add(comboMonth);
		
		comboDay = new JComboBox<>();
		comboDay.setForeground(new Color(71, 71, 71));
		comboDay.setFont(new Font("Dialog", Font.BOLD, 17));
		comboDay.setBounds(451, 635, 130, 45);
		contentPane.add(comboDay);
		
		comboCountries = new JComboBox<>();
		comboCountries.setBounds(51, 733, 368, 45);
		contentPane.add(comboCountries);
		
		lblImage = new JLabel("image");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(438, 733, 102, 45);
		contentPane.add(lblImage);
		
		JButton btnSignIn = new JButton("Sign in");
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
		btnSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSignIn.setForeground(new Color(255, 255, 255));
		btnSignIn.setFont(new Font("Dialog", Font.BOLD, 20));
		btnSignIn.setBounds(174, 824, 285, 55);
		btnSignIn.setBackground(new Color(255, 88, 93));
		btnSignIn.setBorder(null);
		contentPane.add(btnSignIn);
		
		fillComboYear();
		fillComboMonth();
		fillComboDay();
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
}
