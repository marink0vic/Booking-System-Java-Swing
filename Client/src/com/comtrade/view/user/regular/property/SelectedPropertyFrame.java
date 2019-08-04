package com.comtrade.view.user.regular.property;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.view.user.regular.HeaderPanel;
import com.comtrade.view.user.regular.HomePagePanel;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SelectedPropertyFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private PropertyInfoPanel propertyInfoPanel;
	private RoomsPricesPanel roomPricesPanel;
	private ReviewPropertyPanel reviewPropertyPanel;
	private HomePagePanel homePagePanel;
	private HeaderPanel headerPanel = HeaderPanel.getPanel(); 
	//--
	private JLabel lblInfo;
	private JLabel lblRoomPrices;
	private JLabel lblGuestReviews;
	//--
	private PropertyWrapper propertyWrapper;
	private User user;
	private LocalDate checkIn;
	private LocalDate checkOut;
	

	
	public SelectedPropertyFrame(PropertyWrapper propertyWrapper, User user, LocalDate checkIn, LocalDate checkOut) {
		this.user = user;
		this.propertyWrapper = propertyWrapper;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1500, 979);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		contentPane.add(headerPanel);
		
		addPropertyNavigationLabels();
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(252, 165, 1150, 767);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		propertyInfoPanel = new PropertyInfoPanel(propertyWrapper);
		layeredPane.add(propertyInfoPanel, "name_600211209077500");
		
		roomPricesPanel = new RoomsPricesPanel(user, propertyWrapper, checkIn, checkOut, headerPanel);
		roomPricesPanel.setPropertyFrame(this);
		layeredPane.add(roomPricesPanel, "name_604263933603500");
		
		reviewPropertyPanel = new ReviewPropertyPanel(propertyWrapper.getReviews());
		layeredPane.add(reviewPropertyPanel, "name_194976834767000");
		
		UserReviewFrame.getReviewFrame().setPropertyInfo(propertyWrapper);
	}

	private void addPropertyNavigationLabels() {
		lblInfo = new JLabel("PROPERTY INFO");
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				switchPanel(propertyInfoPanel);
				updateUI(lblInfo);
			}
		});
		lblInfo.setBorder(new MatteBorder(0, 0, 0, 2, (Color) ColorConstants.LIGHT_GRAY));
		lblInfo.setForeground(ColorConstants.BLUE);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 18));
		lblInfo.setBounds(352, 101, 253, 43);
		lblInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblInfo);
		
		lblRoomPrices = new JLabel("ROOM & PRICES");
		lblRoomPrices.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Map<Booking, List<BookedRoom>> recentBookingsFomDB = ControllerUI.getController().getBookedRooms();
				Map<Booking, List<BookedRoom>> newBookings = addNewBookings(recentBookingsFomDB);
				roomPricesPanel.getBookingsFromDatabase().putAll(newBookings);
				roomPricesPanel.loadRoomScrollPane();
				switchPanel(roomPricesPanel);
				updateUI(lblRoomPrices);
			}
		});
		lblRoomPrices.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomPrices.setForeground(ColorConstants.GRAY);
		lblRoomPrices.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRoomPrices.setBorder(new MatteBorder(0, 0, 0, 2, (Color) ColorConstants.LIGHT_GRAY));
		lblRoomPrices.setBounds(604, 101, 253, 43);
		lblRoomPrices.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblRoomPrices);
		
		lblGuestReviews = new JLabel("GUEST REVIEWS");
		lblGuestReviews.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				switchPanel(reviewPropertyPanel);
				updateUI(lblGuestReviews);
			}
		});
		lblGuestReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuestReviews.setForeground(ColorConstants.GRAY);
		lblGuestReviews.setFont(new Font("Dialog", Font.BOLD, 18));
		lblGuestReviews.setBounds(856, 101, 253, 43);
		lblGuestReviews.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblGuestReviews);
	}
	
	protected Map<Booking, List<BookedRoom>> addNewBookings(Map<Booking, List<BookedRoom>> recentBookings) {
		Map<Booking, List<BookedRoom>> newBookings = new HashMap<>();
		Iterator<Map.Entry<Booking, List<BookedRoom>>> iterator = recentBookings.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<Booking, List<BookedRoom>> entry = iterator.next();
			Booking b = entry.getKey();
			if (b.getProperty().getIdProperty() == propertyWrapper.getProperty().getIdProperty()) {
				propertyWrapper.addNewBooking(b, entry.getValue());
				newBookings.put(b, entry.getValue());
				iterator.remove();
			}
		}
		return newBookings;
	}

	private void updateUI(JLabel label) {
		JLabel[] navLabels = {lblInfo, lblRoomPrices, lblGuestReviews};
		for (int i = 0; i < navLabels.length; i++) {
			if (label == navLabels[i]) {
				label.setForeground(ColorConstants.BLUE);
			} else {
				navLabels[i].setForeground(ColorConstants.GRAY);
			}
		}
		
	}

	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
}
