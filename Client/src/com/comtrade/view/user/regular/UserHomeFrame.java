package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.dto.UserWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.login.IProxy;
import com.comtrade.view.user.regular.property.UserReviewFrame;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserHomeFrame extends JFrame implements IProxy {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HeaderPanel headerPanel;
	private JLayeredPane layeredPane;
	private HomePagePanel homePagePanel;
	private SearchPagePanel searchPagePanel;
	
	private List<PropertyWrapper> listOfProperties;
	private User user; // loged user
	private UserWrapper userWrapper;
	private JTextField tfSearch;
	private JDateChooser dateCheckIn;
	private JDateChooser dateCheckOut;
	private LocalDate checkIn;
	private LocalDate checkOut;
	
	public UserHomeFrame(User user) {
		this.user = user;
		checkIn = LocalDate.now();
		checkOut = checkIn.plusDays(10);
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 979);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(169, 169, 169)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(252, 204, 975, 728);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		
		returnBookingsForUser();
		loadAllProperties();
		sortPropertiesBasedOnReviewRating();
		setSearchPanel();
		
		headerPanel = new HeaderPanel();
		headerPanel.setUser(user);
		contentPane.add(headerPanel);
		
		homePagePanel = new HomePagePanel(listOfProperties, user, dateCheckIn, dateCheckOut);
		layeredPane.add(homePagePanel, "name_536942772642600");
		
		searchPagePanel = new SearchPagePanel(listOfProperties, user);
		layeredPane.add(searchPagePanel, "name_538271889712300");
		
		UserProfileFrame.getFrame().setBookings(userWrapper);
		UserProfileFrame.getFrame().initializeComponents();
		
		UserReviewFrame.getReviewFrame().setMyBookings(userWrapper.getBookings());
		UserReviewFrame.getReviewFrame().setUser(userWrapper.getUser());
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				List<Booking> acceptedBookings = ControllerUI.getController().getAcceptedBookings();
				UserProfileFrame.getFrame().updateBookings(acceptedBookings);
			}
		});
		thread.start();
	}

	private void setSearchPanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(0, 69, 1482, 135);
		searchPanel.setBackground(new Color(250, 250, 250));
		searchPanel.setLayout(null);
		contentPane.add(searchPanel);
		
		tfSearch = new JTextField();
		tfSearch.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfSearch.setOpaque(false);
		tfSearch.setForeground(ColorConstants.GRAY);
		tfSearch.setFont(new Font("Dialog", Font.PLAIN, 18));
		tfSearch.setBounds(263, 50, 353, 56);
		searchPanel.add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag = true;
				String inputText = tfSearch.getText().trim();
				searchPagePanel.setSearchCityInput(inputText);
				DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				if (dateCheckIn.getDate() != null && dateCheckOut.getDate() != null) {
					setDates(date);
				} else if (dateCheckIn.getDate() == null && dateCheckOut.getDate() == null) {
					setDateChooserValues(date);
				} else {
					flag = false;
					JOptionPane.showMessageDialog(null, "Please enter the seccond date");
				}
				
				if (flag) {
					searchPagePanel.setCheckIn(checkIn);
					searchPagePanel.setCheckOut(checkOut);
					searchPagePanel.listAllProperties();
					switchPanel(searchPagePanel);
				}
			}
		});
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 15));
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.setBackground(ColorConstants.BLUE);
		btnSearch.setBounds(1075, 50, 138, 56);
		searchPanel.add(btnSearch);
		
		JButton btnBackToHome = new JButton("Home");
		btnBackToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchPanel(homePagePanel);
			}
		});
		btnBackToHome.setFont(new Font("Dialog", Font.BOLD, 15));
		btnBackToHome.setForeground(new Color(255, 255, 255));
		btnBackToHome.setBackground(ColorConstants.RED);
		btnBackToHome.setBounds(1225, 50, 138, 56);
		searchPanel.add(btnBackToHome);
		
		JLabel lblWhere = new JLabel("WHERE");
		lblWhere.setFont(new Font("Dialog", Font.BOLD, 15));
		lblWhere.setForeground(new Color(71, 71, 71));
		lblWhere.setBounds(263, 21, 56, 16);
		searchPanel.add(lblWhere);
		
		JLabel lblCheckin = new JLabel("CHECK-IN");
		lblCheckin.setForeground(new Color(71, 71, 71));
		lblCheckin.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckin.setBounds(645, 21, 88, 16);
		searchPanel.add(lblCheckin);
		
		JLabel lblCheckout = new JLabel("CHECKOUT");
		lblCheckout.setForeground(new Color(71, 71, 71));
		lblCheckout.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckout.setBounds(861, 22, 98, 16);
		searchPanel.add(lblCheckout);
		
		dateCheckOut = new JDateChooser();
		dateCheckOut.setForeground(ColorConstants.GRAY);
		dateCheckOut.setFont(new Font("Dialog", Font.BOLD, 16));
		dateCheckOut.setBounds(861, 50, 191, 56);
		searchPanel.add(dateCheckOut);
		
		dateCheckIn = new JDateChooser();
		dateCheckIn.getDateEditor().addPropertyChangeListener(
			new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				dateCheckOut.setCalendar(null);
				dateCheckOut.setMinSelectableDate(dateCheckIn.getDate());
			 }
		});
		dateCheckIn.setMinSelectableDate(new Date());
		dateCheckIn.setForeground(ColorConstants.GRAY);
		dateCheckIn.setFont(new Font("Dialog", Font.BOLD, 16));
		dateCheckIn.setBounds(643, 50, 191, 56);
		searchPanel.add(dateCheckIn);
		
	}
	
	private void setDates(DateFormat date) {
		String in = date.format(dateCheckIn.getDate());
		String out = date.format(dateCheckOut.getDate());
		checkIn = LocalDate.parse(in);
		checkOut = LocalDate.parse(out);
		if (checkIn.isEqual(checkOut)) checkOut = checkIn.plusDays(1);
	}
	
	private void setDateChooserValues(DateFormat date) {
		String chcIn = String.valueOf(checkIn);
		String chcOut = String.valueOf(checkOut);
		try {
			dateCheckIn.setDate(date.parse(chcIn));
			dateCheckOut.setDate(date.parse(chcOut));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void sortPropertiesBasedOnReviewRating() {
		listOfProperties.sort(new Comparator<PropertyWrapper>() {
			@Override
			public int compare(PropertyWrapper wrapper1, PropertyWrapper wrapper2) {
				Double avg1 = calculateAverage(wrapper1.getReviews());
				Double avg2 = calculateAverage(wrapper2.getReviews());
				if (avg1 == avg2) return 0;
				if (avg1 > avg2) return -1;
				else return 1;
			}
		});
	}

	private double calculateAverage(List<PropertyReview> reviews) {
		int sum = reviews.stream().mapToInt(v -> v.getRating()).sum();
		if (sum == 0) return 0.0;
		return (double) sum / reviews.size();
	}

	private void returnBookingsForUser() {
		UserWrapper wrapper = new UserWrapper();
		wrapper.setUser(user);
		
		TransferClass transfer = new TransferClass();
		transfer.setClientRequest(wrapper);
		transfer.setDomainType(DomainType.USER);
		transfer.setOperation(Operations.RETURN_BOOKING_FOR_USER);
		ControllerUI.getController().sendToServer(transfer);

		userWrapper = ControllerUI.getController().getUserWrapper();
		userWrapper.setUser(user);
	}
	
	private void loadAllProperties() {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(transferClass);
		transferClass.setDomainType(DomainType.PROPERTY);
		transferClass.setOperation(Operations.RETURN_ALL);
		ControllerUI.getController().sendToServer(transferClass);
		
		listOfProperties = ControllerUI.getController().getProperties();
	}

	@Override
	public void login(User user) {
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
