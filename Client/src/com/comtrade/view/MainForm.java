package com.comtrade.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final int regularUser = 1;
	private final int host = 2;
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

	/**
	 * Create the frame.
	 */
	public MainForm() {
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(71, 71, 71));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("BOOKING");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 66));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(620, 75, 326, 52);
		contentPane.add(lblNewLabel);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1200, 700);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(71, 71, 71));
		layeredPane.add(loginPanel, "name_12723390239800");
		loginPanel.setLayout(null);
		
		tfUsernane = new JTextField();
		tfUsernane.setFont(new Font("Dialog", Font.BOLD, 20));
		tfUsernane.setText("");
		tfUsernane.setForeground(new Color(71, 71, 71));
		tfUsernane.setColumns(10);
		tfUsernane.setBounds(557, 259, 477, 55);
		loginPanel.add(tfUsernane);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(71, 71, 71));
		passwordField.setFont(new Font("Dialog", Font.BOLD, 15));
		passwordField.setBounds(557, 361, 477, 55);
		loginPanel.add(passwordField);
		
		JButton button = new JButton("Login");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Dialog", Font.PLAIN, 18));
		button.setContentAreaFilled(false);
		button.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		button.setBounds(557, 451, 231, 55);
		loginPanel.add(button);
		
		JButton button_1 = new JButton("Register");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layeredPane.removeAll();
				layeredPane.add(registerPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Dialog", Font.PLAIN, 18));
		button_1.setContentAreaFilled(false);
		button_1.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		button_1.setBounds(803, 451, 231, 55);
		loginPanel.add(button_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 3));
		panel.setBounds(453, 177, 646, 396);
		panel.setBackground(null);
		loginPanel.add(panel);
		panel.setLayout(null);
		
		registerPanel = new JPanel();
		registerPanel.setBackground(new Color(71, 71, 71));
		layeredPane.add(registerPanel, "name_12845655894600");
		registerPanel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(null);
		panel_2.setBounds(455, 177, 656, 387);
		panel_2.setBorder(new LineBorder(new Color(255, 255, 255), 3));
		registerPanel.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnRegister = new JButton("Register as user");
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(new Font("Dialog", Font.PLAIN, 18));
		btnRegister.setContentAreaFilled(false);
		btnRegister.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnRegister.setBounds(82, 172, 231, 55);
		panel_2.add(btnRegister);
		
		JButton btnHost = new JButton("Become a host");
		btnHost.setForeground(Color.WHITE);
		btnHost.setFont(new Font("Dialog", Font.PLAIN, 18));
		btnHost.setContentAreaFilled(false);
		btnHost.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnHost.setBounds(371, 172, 231, 55);
		panel_2.add(btnHost);
	}
}
