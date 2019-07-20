package com.comtrade.view.user.host;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.login.IProxy;

import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;

public class PropertyOwnerFrame extends JFrame implements IProxy {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//---top panel
	private JLabel mainTextHeader;
	//---left panels
	private JPanel homePanelLeft;
	private JPanel availabilityPanel;
	private JPanel reservationPanel;
	private JPanel messagePanel;
	private JPanel earningsPanel;
	private JPanel currentPanel;
	//-----right panels
	private HomePanel homePanelRight;
	
	private PropertyWrapper propertyWrapper;
	private User user;

	/**
	 * Create the frame.
	 */
	public PropertyOwnerFrame(User user, PropertyWrapper propertyWrapper) {
		this.user = user;
		this.propertyWrapper = propertyWrapper;
		initializeComponents();
	}
	
	public PropertyOwnerFrame(User user) {
		this.user = user;
		propertyWrapper = new PropertyWrapper();
		propertyWrapper.setUserID(user.getIdUser());
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 850);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		createSidePanel();
		
		if (propertyWrapper.getProperty() == null)returnPropertyForUser();
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(350, 136, 1132, 667);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		homePanelRight = new HomePanel(user, propertyWrapper);
		layeredPane.add(homePanelRight, "name_148133244248700");
		
		
		
		JPanel HeaderTextPanel = new JPanel();
		HeaderTextPanel.setBackground(new Color(95, 139, 161));
		HeaderTextPanel.setBounds(350, 0, 1132, 136);
		contentPane.add(HeaderTextPanel);
		HeaderTextPanel.setLayout(null);
		
		mainTextHeader = new JLabel("Home");
		mainTextHeader.setForeground(new Color(255, 255, 255));
		mainTextHeader.setFont(new Font("Dialog", Font.BOLD, 44));
		mainTextHeader.setHorizontalAlignment(SwingConstants.CENTER);
		mainTextHeader.setBounds(338, 33, 439, 76);
		HeaderTextPanel.add(mainTextHeader);
		
		
	}

	private void createSidePanel() {
		boolean activePanel[] = {false, false, false, false, false};
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(32, 40, 44));
		headerPanel.setBounds(0, 0, 350, 803);
		contentPane.add(headerPanel);
		headerPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEADING);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(48, 58, 230, 64);
		lblNewLabel.setText(user.getFirstName() + " " + user.getLastName());
		ImageIcon myImage = new ImageIcon(user.getCountry().getImage());
		lblNewLabel.setIcon(myImage);
		headerPanel.add(lblNewLabel);
		
		homePanelLeft = new JPanel();
		homePanelLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (!activePanel[0])
					homePanelLeft.setBackground(new Color(46, 63, 71));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				if (!activePanel[0])
					homePanelLeft.setBackground(new Color(32, 40, 44));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPanel.setBackground(new Color(32, 40, 44));
				activePanel[0] = true;
				changeSelectedBackgroundColor(activePanel, 0);
				homePanelLeft.setBackground(new Color(95, 139, 161));
				currentPanel = homePanelLeft;
				mainTextHeader.setText("Home");
			}
		});
		homePanelLeft.setBorder(null);
		homePanelLeft.setBackground(new Color(95, 139, 161));
		homePanelLeft.setBounds(0, 182, 350, 59);
		headerPanel.add(homePanelLeft);
		homePanelLeft.setLayout(null);
		currentPanel = homePanelLeft;
		
		JLabel homeIconLbl = new JLabel("");
		homeIconLbl.setBorder(null);
		homeIconLbl.setBounds(54, 13, 32, 32);
		homePanelLeft.add(homeIconLbl);
		Icon icon = new ImageIcon("./resources/icons/home.png");
		homeIconLbl.setIcon(icon);
		
		JLabel lblProperty = new JLabel("Home");
		lblProperty.setForeground(new Color(255, 255, 255));
		lblProperty.setFont(new Font("Dialog", Font.BOLD, 17));
		lblProperty.setBounds(130, 13, 145, 32);
		homePanelLeft.add(lblProperty);
		
		availabilityPanel = new JPanel();
		availabilityPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!activePanel[1])
					availabilityPanel.setBackground(new Color(46, 63, 71));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (!activePanel[1])
					availabilityPanel.setBackground(new Color(32, 40, 44));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPanel.setBackground(new Color(32, 40, 44));
				activePanel[1] = true;
				changeSelectedBackgroundColor(activePanel, 1);
				availabilityPanel.setBackground(new Color(95, 139, 161));
				currentPanel = availabilityPanel;
				mainTextHeader.setText("Availability");
			}
		});
		availabilityPanel.setLayout(null);
		availabilityPanel.setBorder(null);
		availabilityPanel.setBackground(new Color(32, 40, 44));
		availabilityPanel.setBounds(0, 241, 350, 59);
		headerPanel.add(availabilityPanel);
		
		JLabel calendarIconLbl = new JLabel("");
		calendarIconLbl.setBorder(null);
		calendarIconLbl.setBounds(54, 13, 32, 32);
		Icon iconCal = new ImageIcon("./resources/icons/calendar.png");
		calendarIconLbl.setIcon(iconCal);
		availabilityPanel.add(calendarIconLbl);
		
		JLabel lblAvailability = new JLabel("Availability");
		lblAvailability.setForeground(Color.WHITE);
		lblAvailability.setFont(new Font("Dialog", Font.BOLD, 17));
		lblAvailability.setBounds(130, 13, 145, 32);
		availabilityPanel.add(lblAvailability);
		
		reservationPanel = new JPanel();
		reservationPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!activePanel[2])
					reservationPanel.setBackground(new Color(46, 63, 71));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (!activePanel[2])
					reservationPanel.setBackground(new Color(32, 40, 44));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPanel.setBackground(new Color(32, 40, 44));
				activePanel[2] = true;
				changeSelectedBackgroundColor(activePanel, 2);
				reservationPanel.setBackground(new Color(95, 139, 161));
				currentPanel = reservationPanel;
				mainTextHeader.setText("Reservations");
			}
		});
		reservationPanel.setLayout(null);
		reservationPanel.setBorder(null);
		reservationPanel.setBackground(new Color(32, 40, 44));
		reservationPanel.setBounds(0, 301, 350, 59);
		headerPanel.add(reservationPanel);
		
		JLabel reservationIconLbl = new JLabel("");
		reservationIconLbl.setBorder(null);
		reservationIconLbl.setBounds(54, 13, 32, 32);
		Icon iconReservation = new ImageIcon("./resources/icons/reservation.png");
		reservationIconLbl.setIcon(iconReservation);
		reservationPanel.add(reservationIconLbl);
		
		JLabel lblReservation = new JLabel("Reservations");
		lblReservation.setForeground(Color.WHITE);
		lblReservation.setFont(new Font("Dialog", Font.BOLD, 17));
		lblReservation.setBounds(130, 13, 145, 32);
		reservationPanel.add(lblReservation);
		
		messagePanel = new JPanel();
		messagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!activePanel[3])
					messagePanel.setBackground(new Color(46, 63, 71));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (!activePanel[3])
					messagePanel.setBackground(new Color(32, 40, 44));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPanel.setBackground(new Color(32, 40, 44));
				activePanel[3] = true;
				changeSelectedBackgroundColor(activePanel, 3);
				messagePanel.setBackground(new Color(95, 139, 161));
				currentPanel = messagePanel;
				mainTextHeader.setText("Messages");
			}
		});
		messagePanel.setLayout(null);
		messagePanel.setBorder(null);
		messagePanel.setBackground(new Color(32, 40, 44));
		messagePanel.setBounds(0, 361, 350, 59);
		headerPanel.add(messagePanel);
		
		JLabel inboxIconLbl = new JLabel("");
		inboxIconLbl.setBorder(null);
		inboxIconLbl.setBounds(54, 13, 32, 32);
		Icon iconInbox = new ImageIcon("./resources/icons/inbox.png");
		inboxIconLbl.setIcon(iconInbox);
		messagePanel.add(inboxIconLbl);
		
		JLabel lblMessages = new JLabel("Messages");
		lblMessages.setForeground(Color.WHITE);
		lblMessages.setFont(new Font("Dialog", Font.BOLD, 17));
		lblMessages.setBounds(130, 13, 145, 32);
		messagePanel.add(lblMessages);
		
		earningsPanel = new JPanel();
		earningsPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!activePanel[4])
					earningsPanel.setBackground(new Color(46, 63, 71));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (!activePanel[4])
				  earningsPanel.setBackground(new Color(32, 40, 44));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPanel.setBackground(new Color(32, 40, 44));
				activePanel[4] = true;
				changeSelectedBackgroundColor(activePanel, 4);
				earningsPanel.setBackground(new Color(95, 139, 161));
				currentPanel = earningsPanel;
				mainTextHeader.setText("Earnings");
			}
		});
		earningsPanel.setLayout(null);
		earningsPanel.setBorder(null);
		earningsPanel.setBackground(new Color(32, 40, 44));
		earningsPanel.setBounds(0, 419, 350, 59);
		headerPanel.add(earningsPanel);
		
		JLabel financeIconLbl = new JLabel("");
		financeIconLbl.setBorder(null);
		financeIconLbl.setBounds(54, 13, 32, 32);
		Icon iconFinance = new ImageIcon("./resources/icons/finance.png");
		financeIconLbl.setIcon(iconFinance);
		earningsPanel.add(financeIconLbl);
		
		JLabel lblInances = new JLabel("Earnings");
		lblInances.setForeground(Color.WHITE);
		lblInances.setFont(new Font("Dialog", Font.BOLD, 17));
		lblInances.setBounds(130, 13, 145, 32);
		earningsPanel.add(lblInances);
		
		
	}
	
	private void returnPropertyForUser() {
		try {
			TransferClass transferClass = ControllerUI.getController().returnPropertyForOwner(propertyWrapper);
			propertyWrapper = (PropertyWrapper) transferClass.getServerResponse();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private void changeSelectedBackgroundColor(boolean[] activePanel, int index) {
		for (int i = 0; i < activePanel.length; i++) {
			if (i != index) {
				activePanel[i] = false;
			}
		}
		
	}

	@Override
	public void login(User user) {
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
