package com.comtrade.view.user.admin;


import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.User;
import com.comtrade.dto.AdminWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.util.ImageResize;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AdminFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private JLabel lblHome;
	private JLabel lblProperties;
	private JLabel lblUsers;
	private JLabel tempLabel;
	private HomePanel homePanel;
	private PropertiesPanel propertiesPanel;
	private EarningsPanel earningsPanel;
	private User user;
	private AdminWrapper adminWrapper;

	
	public AdminFrame(User user) {
		this.user = user;
		loadInformationFromDB();
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 850);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setSideLabels();
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(435, 0, 1047, 803);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		homePanel = new HomePanel(adminWrapper);
		propertiesPanel = new PropertiesPanel(adminWrapper);
		earningsPanel = new EarningsPanel(adminWrapper.getTransactions());
		
		layeredPane.add(homePanel, "name_1132329764788000");
		layeredPane.add(propertiesPanel, "name_1133506876717400");
		layeredPane.add(earningsPanel, "name_1133538685523500");
	}

	private void setSideLabels() {
		JLabel lblProfilePic = new JLabel("");
		lblProfilePic.setBorder(null);
		lblProfilePic.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblProfilePic.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfilePic.setBounds(124, 35, 175, 150);
		File f = new File(user.getProfilePicture());
		Icon icon = ImageResize.resizeImage(f, lblProfilePic.getWidth(), lblProfilePic.getHeight());
		lblProfilePic.setIcon(icon);
		contentPane.add(lblProfilePic);
		
		JLabel lblName = new JLabel(user.getFirstName() + " " + user.getLastName());
		lblName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblName.setForeground(ColorConstants.GRAY);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(72, 198, 280, 70);
		contentPane.add(lblName);
		
		lblHome = new JLabel("HOME");
		lblHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lblHome.setForeground(ColorConstants.BLUE);
				tempLabel.setForeground(ColorConstants.LIGHT_GRAY);
				tempLabel = lblHome;
				switchPanel(homePanel);
			}
		});
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblHome.setForeground(ColorConstants.BLUE);
		lblHome.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblHome.setBounds(124, 336, 170, 70);
		tempLabel = lblHome;
		contentPane.add(lblHome);
		
		lblProperties = new JLabel("PROPERTIES");
		lblProperties.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblProperties.setForeground(ColorConstants.BLUE);
				tempLabel.setForeground(ColorConstants.LIGHT_GRAY);
				tempLabel = lblProperties;
				switchPanel(propertiesPanel);
			}
		});
		lblProperties.setHorizontalAlignment(SwingConstants.CENTER);
		lblProperties.setForeground(ColorConstants.LIGHT_GRAY);
		lblProperties.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblProperties.setBounds(124, 454, 170, 70);
		contentPane.add(lblProperties);
		
		lblUsers = new JLabel("EARNINGS");
		lblUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblUsers.setForeground(ColorConstants.BLUE);
				tempLabel.setForeground(ColorConstants.LIGHT_GRAY);
				tempLabel = lblUsers;
				switchPanel(earningsPanel);
			}
		});
		lblUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsers.setForeground(ColorConstants.LIGHT_GRAY);
		lblUsers.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblUsers.setBounds(124, 573, 170, 70);
		contentPane.add(lblUsers);
	}
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	private void loadInformationFromDB() {
		TransferClass transfer = new TransferClass();
		transfer.setDomainType(DomainType.USER);
		transfer.setOperation(Operations.RETURN_ALL_INFO_FOR_ADMIN);
		ControllerUI.getController().sendToServer(transfer);
		
		adminWrapper = ControllerUI.getController().getAdminWrapper();
	}
}
