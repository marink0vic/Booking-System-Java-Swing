package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public HeaderPanel() {
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(0, 0, 1482, 69);
		this.setBackground(new Color(95, 139, 161));
		this.setLayout(null);
		
		JLabel lblLogo = new JLabel("Booking.com");
		lblLogo.setFont(new Font("Dialog", Font.BOLD, 26));
		lblLogo.setForeground(new Color(255, 255, 255));
		lblLogo.setBounds(50, 13, 184, 43);
		this.add(lblLogo);
		
	}
}
