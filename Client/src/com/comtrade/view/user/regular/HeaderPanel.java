package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.Booking;
import com.comtrade.domain.User;
import com.comtrade.dto.UserWrapper;
import com.comtrade.view.user.regular.property.RoomsPricesPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;
import java.awt.Cursor;

public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final HeaderPanel panel = new HeaderPanel();
	private UserWrapper userWrapper;
	private JLabel lblLogedUser;
	private JLabel imageLabel;
	private JLabel lblNew;

	private HeaderPanel() {
		initializeComponents();
	}
	
	public static HeaderPanel getPanel() {
		return panel;
	}
	
	public UserWrapper getUserWrapper() {
		return userWrapper;
	}
	
	public JLabel getLblNew() {
		return lblNew;
	}

	public void setUserWrapper(UserWrapper userWrapper) {
		this.userWrapper = userWrapper;
		setProfileNameLabel();
		imageLabel.setIcon(setIcon());
	}

	private void initializeComponents() {
		this.setBounds(0, 0, 1482, 69);
		this.setBackground(ColorConstants.BLUE);
		this.setLayout(null);
		
		JLabel lblLogo = new JLabel("Booking.com");
		lblLogo.setFont(new Font("Dialog", Font.BOLD, 26));
		lblLogo.setForeground(new Color(255, 255, 255));
		lblLogo.setBounds(50, 13, 184, 43);
		this.add(lblLogo);
		
		imageLabel = new JLabel("B");
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(1169, 20, 50, 28);
		imageLabel.setBorder(null);
		add(imageLabel);
		
		lblNew = new JLabel("");
		lblNew.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNew.setForeground(ColorConstants.BLUE);
		lblNew.setBackground(ColorConstants.BLUE);
		lblNew.setHorizontalAlignment(SwingConstants.CENTER);
		lblNew.setOpaque(true);
		lblNew.setBounds(1426, 13, 44, 43);
		add(lblNew);
		
		lblLogedUser = new JLabel("");
		lblLogedUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblNew.setForeground(ColorConstants.BLUE);
				lblNew.setBackground(ColorConstants.BLUE);
				RoomsPricesPanel.numOfCompleteBookings = 0;
				UserProfileFrame userFrame = UserProfileFrame.getFrame();
				userFrame.setLocationRelativeTo(null);
				userFrame.setVisible(true);
			}
		});
		lblLogedUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogedUser.setForeground(new Color(255, 255, 255));
		lblLogedUser.setFont(new Font("Dialog", Font.BOLD, 16));
		lblLogedUser.setBounds(1236, 13, 234, 43);
		
		add(lblLogedUser);
		
	}
	
	private Icon setIcon() {
		File file = new File(userWrapper.getUser().getCountry().getImage());
		ImageIcon imgIcon = new ImageIcon(file.getPath());
		Image img = imgIcon.getImage();
		Image newImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	private void setProfileNameLabel() {
		lblLogedUser.setText(userWrapper.getUser().getFirstName() + " " + userWrapper.getUser().getLastName());
	}
}
