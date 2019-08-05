package com.comtrade.view.user.host;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.Message;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.login.IProxy;
import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PropertyOwnerFrame extends JFrame implements IProxy {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	//---top panel
	private JLabel mainTextHeader;
	//---left panels
	private JPanel homePanelLeft;
	private JPanel availabilityPanel;
	private JPanel reservationPanel;
	private JPanel messagePanel;
	private JPanel earningsPanel;
	private JPanel reviewPanel;
	private JPanel currentPanel;
	//-----right panels
	private HomePanel homePanelRight;
	private ReservationPanel reservationPanelRight;
	private AvailabilityPanel availabilityPanelRight;
	private EarningsPanel earningsPanelRight;
	private ReviewPanel reviewPanelRight;
	private JPanel MessagePanelRight;
	//------
	private PropertyWrapper propertyWrapper;
	private User user;
	private Set<RoomType> roomTypes;
	private  Map<Booking, List<BookedRoom>> oldBookings;
	private  Map<Booking, List<BookedRoom>> newBookings;
	private JLabel lblNewRes;
	private JLabel lblNewReviews;
	private int reviewCount;
	private JTextArea textAreaReceive;
	private JTextArea textAreaSend;
	private Message message;
	private String messageToSend;
	private User userSender;
	//----
	
	
	/**
	 * Create the frame.
	 */
	public PropertyOwnerFrame(PropertyWrapper propertyWrapper) {
		this.user = propertyWrapper.getUser();
		this.propertyWrapper = propertyWrapper;
		initializeComponents();
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public PropertyOwnerFrame(User user) {
		this.user = user;
		propertyWrapper = new PropertyWrapper();
		propertyWrapper.setUser(user);
		initializeComponents();
	}

	private void initializeComponents() {
		if (propertyWrapper.getProperty() == null) returnPropertyForUser();
		this.roomTypes = propertyWrapper.getRooms().keySet();
		oldBookings = new HashMap<>();
		newBookings = new HashMap<>();
		addBookingsToCorrectMap();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 850);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		createSidePanel();
		
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(350, 136, 1132, 667);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		homePanelRight = new HomePanel(propertyWrapper);
		layeredPane.add(homePanelRight, "name_148133244248700");
		
		reservationPanelRight = new ReservationPanel(roomTypes, oldBookings, newBookings, lblNewRes);
		layeredPane.add(reservationPanelRight, "name_90023259817100");
		
		availabilityPanelRight = new AvailabilityPanel(roomTypes, oldBookings);
		layeredPane.add(availabilityPanelRight, "name_89976615789600");
		//----------------------------------
		
		MessagePanelRight = new JPanel();
		MessagePanelRight.setBackground(new Color(255, 255, 255));
		layeredPane.add(MessagePanelRight, "name_179496146822800");
		MessagePanelRight.setLayout(null);
		
		textAreaReceive = new JTextArea();
		textAreaReceive.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		textAreaReceive.setForeground(ColorConstants.GRAY);
		textAreaReceive.setFont(new Font("Dialog", Font.BOLD, 19));
		textAreaReceive.setBounds(152, 92, 519, 507);
		textAreaReceive.setLineWrap(true);
		textAreaReceive.setWrapStyleWord(true);
		textAreaReceive.setEditable(false);
		MessagePanelRight.add(textAreaReceive);
		
		textAreaSend = new JTextArea();
		textAreaSend.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		textAreaSend.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					messageToSend = textAreaSend.getText();
					if (messageToSend.length() > 0) {
						sendMessageToClient();
					}
				}
			}
		});
		textAreaSend.setForeground(ColorConstants.GRAY);
		textAreaSend.setFont(new Font("Dialog", Font.BOLD, 19));
		textAreaSend.setBounds(707, 92, 358, 189);
		textAreaSend.setLineWrap(true);
		textAreaSend.setWrapStyleWord(true);
		MessagePanelRight.add(textAreaSend);
		
		JButton btnNewButton = new JButton("SEND");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				messageToSend = textAreaSend.getText();
				if (messageToSend.length() > 0) {
					sendMessageToClient();
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 20));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(ColorConstants.BLUE);
		btnNewButton.setBounds(707, 325, 360, 79);
		MessagePanelRight.add(btnNewButton);
		
		//--------------------------------------
		earningsPanelRight = new EarningsPanel(oldBookings, propertyWrapper.getProperty());
		layeredPane.add(earningsPanelRight, "name_179515782969900");
			
		reviewPanelRight = new ReviewPanel(propertyWrapper.getReviews());
		layeredPane.add(reviewPanelRight, "name_270003645240700");
		
		
		
		//-----------------------------------------------------------
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
	
		ControllerUI.getController().setOwnerFrame(this);
		
		Thread messageThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					message = ControllerUI.getController().getMessage();
					showMessageToTextArea();
				}
			}
		});
		
		messageThread.start();
	}
	
	private void showMessageToTextArea() {
		userSender = message.getSender();
		String name = userSender.getFirstName() + " " + userSender.getLastName() + ": ";
		String ms = name + message.getMessage();
		textAreaReceive.append(ms + "\n");
	}

	private void sendMessageToClient() {
		textAreaReceive.append("Me: " + messageToSend + "\n");
		textAreaSend.setText("");	
		Message message = new Message(user, userSender, messageToSend);
		TransferClass transfer = new TransferClass();
		transfer.setClientRequest(message);
		transfer.setDomainType(DomainType.USER);
		transfer.setOperation(Operations.MESSAGE);
		
		ControllerUI.getController().sendToServer(transfer);
	}

	private void createSidePanel() {
		boolean activePanel[] = {false, false, false, false, false, false};
		
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
				switchPanel(homePanelRight);
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
				switchPanel(availabilityPanelRight);
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
				switchPanel(reservationPanelRight);
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
		lblReservation.setBounds(130, 13, 119, 32);
		reservationPanel.add(lblReservation);
		
		lblNewRes = new JLabel("");
		lblNewRes.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewRes.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewRes.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewRes.setOpaque(true);
		lblNewRes.setForeground(Color.WHITE);
		lblNewRes.setBackground(null);
		lblNewRes.setBounds(260, 13, 42, 32);
		if (newBookings.size() > 0) {
			lblNewRes.setBackground(ColorConstants.RED);
			lblNewRes.setText(String.valueOf(newBookings.size()));
		}
		reservationPanel.add(lblNewRes);
		
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
				switchPanel(MessagePanelRight);
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
				switchPanel(earningsPanelRight);
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
		
		reviewPanel = new JPanel();
		reviewPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!activePanel[5])
					reviewPanel.setBackground(new Color(46, 63, 71));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (!activePanel[5])
					reviewPanel.setBackground(new Color(32, 40, 44));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				currentPanel.setBackground(new Color(32, 40, 44));
				activePanel[5] = true;
				changeSelectedBackgroundColor(activePanel, 5);
				reviewPanel.setBackground(new Color(95, 139, 161));
				currentPanel = reviewPanel;
				mainTextHeader.setText("Reviews");
				
				lblNewReviews.setText("");
				lblNewReviews.setBackground(null);
				reviewCount = 0;
				switchPanel(reviewPanelRight);
			}
		});
		reviewPanel.setLayout(null);
		reviewPanel.setBorder(null);
		reviewPanel.setBackground(new Color(32, 40, 44));
		reviewPanel.setBounds(0, 479, 350, 59);
		headerPanel.add(reviewPanel);
		
		JLabel lblReviewIcon = new JLabel("");
		lblReviewIcon.setBorder(null);
		lblReviewIcon.setBounds(54, 13, 32, 32);
		Icon iconReview = new ImageIcon("./resources/icons/review-icon.png");
		lblReviewIcon.setIcon(iconReview);
		reviewPanel.add(lblReviewIcon);
		
		JLabel lblReviews = new JLabel("Reviews");
		lblReviews.setForeground(Color.WHITE);
		lblReviews.setFont(new Font("Dialog", Font.BOLD, 17));
		lblReviews.setBounds(130, 13, 91, 32);
		reviewPanel.add(lblReviews);
		
		lblNewReviews = new JLabel("");
		lblNewReviews.setOpaque(true);
		lblNewReviews.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewReviews.setForeground(Color.WHITE);
		lblNewReviews.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewReviews.setBackground(null);
		lblNewReviews.setBounds(260, 13, 42, 32);
		reviewPanel.add(lblNewReviews);
	}
	
	private void returnPropertyForUser() {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(propertyWrapper);
		transferClass.setDomainType(DomainType.PROPERTY);
		transferClass.setOperation(Operations.RETURN_PROPERTY_FOR_OWNER);
		ControllerUI.getController().sendToServer(transferClass);
		
		propertyWrapper = ControllerUI.getController().getPropertyWrapper();
	}
	
	private void addBookingsToCorrectMap() {
		for (Map.Entry<Booking, List<BookedRoom>> entry : propertyWrapper.getBookings().entrySet()) {
			Booking b = entry.getKey();
			if (b.getStatus().equals("PENDING")) {
				newBookings.put(b, entry.getValue());
			} else {
				oldBookings.put(b, entry.getValue());
			}
		}
	}
	
	private void changeSelectedBackgroundColor(boolean[] activePanel, int index) {
		for (int i = 0; i < activePanel.length; i++) {
			if (i != index) {
				activePanel[i] = false;
			}
		}
	}

	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	@Override
	public void login(User user) {
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void signalNewBooking(PropertyWrapper property_wrapper) {
		Map<Booking, List<BookedRoom>> temp = property_wrapper.getBookings();
		if (temp.size() > 0) {
			reservationPanelRight.addNewBookings(temp);
		}
	}

	public void signalNewReview(PropertyReview propertyReview) {
		reviewCount++;
		lblNewReviews.setText(reviewCount+"");
		lblNewReviews.setBackground(ColorConstants.RED);
		reviewPanelRight.addNewReview(propertyReview);
	}
}
