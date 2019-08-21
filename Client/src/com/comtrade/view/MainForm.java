package com.comtrade.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.User;
import com.comtrade.thread.ServerProcessThread;
import com.comtrade.view.login.IProxy;
import com.comtrade.view.login.ProxyLogin;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final String USER_REGULAR = "USER";
	private final String USER_HOST = "SUPER_USER";
	private JTextField tfUsernane;
	private JPasswordField passwordField;
	private JPanel registerPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainForm() {
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1004, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(71, 71, 71));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 174, 986, 479);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 986, 175);
		contentPane.add(panel);
		panel.setBackground(ColorConstants.BLUE);
		panel.setLayout(null);
		
		JLabel lblSmallText = new JLabel("Access your account and start vacation now");
		lblSmallText.setBounds(306, 104, 350, 24);
		panel.add(lblSmallText);
		lblSmallText.setHorizontalAlignment(SwingConstants.CENTER);
		lblSmallText.setForeground(new Color(255, 255, 255));
		lblSmallText.setFont(new Font("Dialog", Font.PLAIN, 18));
		
		JLabel lblNewLabel = new JLabel("Welcome to Booking.com");
		lblNewLabel.setBounds(188, 31, 614, 64);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 50));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		layeredPane.add(loginPanel, "name_12723390239800");
		loginPanel.setLayout(null);
		
		tfUsernane = new JTextField();
		tfUsernane.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfUsernane.setFont(new Font("Dialog", Font.BOLD, 20));
		tfUsernane.setText("");
		tfUsernane.setForeground(new Color(71, 71, 71));
		tfUsernane.setColumns(10);
		tfUsernane.setBounds(253, 126, 477, 55);
		loginPanel.add(tfUsernane);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		passwordField.setForeground(new Color(71, 71, 71));
		passwordField.setFont(new Font("Dialog", Font.BOLD, 15));
		passwordField.setBounds(253, 231, 477, 55);
		loginPanel.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(ColorConstants.RED);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = tfUsernane.getText();
				String password = String.copyValueOf(passwordField.getPassword());
				User user = new User();
				user.setPassword(password);
				user.setUsername(username);
				IProxy proxy = new ProxyLogin(MainForm.this);
				proxy.login(user);
			}
		});
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 18));
		btnLogin.setBounds(253, 321, 231, 55);
		loginPanel.add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layeredPane.removeAll();
				layeredPane.add(registerPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		btnRegister.setBackground(ColorConstants.RED);
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(new Font("Dialog", Font.PLAIN, 18));
		btnRegister.setBounds(499, 321, 231, 55);
		loginPanel.add(btnRegister);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalTextPosition(SwingConstants.LEADING);
		lblUsername.setForeground(new Color(71, 71, 71));
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblUsername.setBounds(253, 75, 97, 38);
		loginPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalTextPosition(SwingConstants.LEADING);
		lblPassword.setForeground(new Color(71, 71, 71));
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblPassword.setBounds(253, 186, 97, 38);
		loginPanel.add(lblPassword);
		
		registerPanel = new JPanel();
		registerPanel.setBackground(Color.WHITE);
		layeredPane.add(registerPanel, "name_12845655894600");
		registerPanel.setLayout(null);
		
		JButton btnRegUser = new JButton("Register as user");
		btnRegUser.setBounds(209, 188, 231, 82);
		registerPanel.add(btnRegUser);
		btnRegUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterForm registerForm = new RegisterForm(USER_REGULAR);
				registerForm.setLocationRelativeTo(null);
				registerForm.setVisible(true);
				dispose();
			}
		});
		btnRegUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegUser.setForeground(Color.WHITE);
		btnRegUser.setBackground(ColorConstants.RED);
		btnRegUser.setFont(new Font("Dialog", Font.PLAIN, 18));
		
		JButton btnHost = new JButton("Become a host");
		btnHost.setBounds(544, 188, 231, 82);
		registerPanel.add(btnHost);
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterForm registerForm = new RegisterForm(USER_HOST);
				registerForm.setLocationRelativeTo(null);
				registerForm.setVisible(true);
				dispose();
			}
		});
		btnHost.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHost.setForeground(Color.WHITE);
		btnHost.setBackground(ColorConstants.RED);
		btnHost.setFont(new Font("Dialog", Font.PLAIN, 18));
		
		ServerProcessThread serverProcess = new ServerProcessThread();
		Thread thread = new Thread(serverProcess);
		thread.start();
	}
}
