package com.comtrade.view.property;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.domain.User;

import javax.swing.JLabel;
import java.awt.Font;

public class PropertyForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;

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
		setBounds(100, 100, 1300, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Welcome " + user.getFirstName() + " " + user.getLastName());
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 21));
		lblNewLabel.setBounds(310, 239, 327, 120);
		contentPane.add(lblNewLabel);
		
	}

}
