package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.comtrade.domain.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private User user;
	
	public HeaderPanel(User user) {
		this.user = user;
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
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(1167, 13, 63, 43);
		imageLabel.setBorder(new LineBorder(Color.WHITE));
		add(imageLabel);
		
		JLabel lblLogedUser = new JLabel("");
		lblLogedUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogedUser.setForeground(new Color(255, 255, 255));
		lblLogedUser.setFont(new Font("Dialog", Font.BOLD, 16));
		lblLogedUser.setBounds(1236, 13, 234, 43);
		lblLogedUser.setText(user.getFirstName() + " " + user.getLastName());
		add(lblLogedUser);
		
	}
}
