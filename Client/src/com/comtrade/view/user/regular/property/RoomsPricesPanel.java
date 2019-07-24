package com.comtrade.view.user.regular.property;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.ColorConstants;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.Transaction;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;

public class RoomsPricesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Map<RoomType, Room> rooms;
	private User user;
	private PropertyWrapper propertyWrapper;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private int period;
	
	private double fullPrice;
	//----
	private JPanel roomInfoPanel;
	private JLabel lblNewLabel;
	private JLabel lblAvailableRooms;
	private JLabel lblPricePerNight;
	private JLabel lblQuantity;
	private JLabel lblRoomType;
	private JLabel lblRoomsAvaiable;
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblAdultsNum;
	private JSeparator separator_7;
	private JLabel lblConfirm;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private JLabel lblNumberOfRooms;
	private JSpinner spinnerRoomCount;

	public RoomsPricesPanel(User user, PropertyWrapper propertyWrapper, LocalDate checkIn, LocalDate checkOut) {
		this.user = user;
		this.propertyWrapper = propertyWrapper;
		this.rooms = propertyWrapper.getRooms();
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		Period p = Period.between(checkIn, checkOut);
		period = p.getDays();
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(252, 165, 975, 767);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		loadTopSectionPanel();
		loadRoomScrollPane();
	}
	
	private void loadTopSectionPanel() {
		roomInfoPanel = new JPanel();
		roomInfoPanel.setBackground(ColorConstants.BLUE);
		roomInfoPanel.setBounds(0, 0, 975, 70);
		this.add(roomInfoPanel);
		roomInfoPanel.setLayout(null);
		
		lblNewLabel = new JLabel("Room type");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(51, 17, 142, 40);
		roomInfoPanel.add(lblNewLabel);
		
		lblAvailableRooms = new JLabel("Number of guests");
		lblAvailableRooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvailableRooms.setForeground(Color.WHITE);
		lblAvailableRooms.setFont(new Font("Dialog", Font.BOLD, 17));
		lblAvailableRooms.setBackground(Color.WHITE);
		lblAvailableRooms.setBounds(266, 17, 199, 40);
		roomInfoPanel.add(lblAvailableRooms);
		
		lblPricePerNight = new JLabel("Price for "+ period +" nights");
		lblPricePerNight.setHorizontalAlignment(SwingConstants.CENTER);
		lblPricePerNight.setForeground(Color.WHITE);
		lblPricePerNight.setFont(new Font("Dialog", Font.BOLD, 17));
		lblPricePerNight.setBackground(Color.WHITE);
		lblPricePerNight.setBounds(501, 17, 195, 40);
		roomInfoPanel.add(lblPricePerNight);
		
		lblQuantity = new JLabel("Quantity");
		lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity.setForeground(Color.WHITE);
		lblQuantity.setFont(new Font("Dialog", Font.BOLD, 17));
		lblQuantity.setBackground(Color.WHITE);
		lblQuantity.setBounds(722, 17, 93, 40);
		roomInfoPanel.add(lblQuantity);
		
		JSeparator separator = new JSeparator();
		separator.setOpaque(true);
		separator.setBounds(236, 17, 1, 40);
		roomInfoPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOpaque(true);
		separator_1.setBounds(488, 17, 1, 40);
		roomInfoPanel.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOpaque(true);
		separator_2.setBounds(700, 17, 1, 40);
		roomInfoPanel.add(separator_2);
		
		separator_7 = new JSeparator();
		separator_7.setOpaque(true);
		separator_7.setBounds(837, 17, 1, 40);
		roomInfoPanel.add(separator_7);
		
		lblConfirm = new JLabel("Confirm");
		lblConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirm.setForeground(Color.WHITE);
		lblConfirm.setFont(new Font("Dialog", Font.BOLD, 17));
		lblConfirm.setBackground(Color.WHITE);
		lblConfirm.setBounds(860, 17, 93, 40);
		roomInfoPanel.add(lblConfirm);
	}

	private void loadRoomScrollPane() {
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(-1, 1));
		gridPanel.setBackground(new Color(255, 255, 255));
		gridPanel.setBounds(0, 71, 975, 700);
	    gridPanel.setAutoscrolls(true);
	    
	    
	    JScrollPane scrollPane = new JScrollPane(gridPanel);
	    scrollPane.setBorder(null);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBounds(0, 71, 975, 700);
	    
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(new Color(255, 255, 255));
		container.setBounds(0, 71, 975, 700);
		container.add(scrollPane, BorderLayout.CENTER);
		this.add(container);
		
		for (Map.Entry<RoomType, Room> entry : rooms.entrySet()) {
			RoomType roomType = entry.getKey();
			Room value = entry.getValue();
			JPanel scrollPanel = addRoomPanelToScrollPane(roomType, value);
            gridPanel.add(scrollPanel);
		}
	}
	
	private JPanel addRoomPanelToScrollPane(RoomType roomType, Room room) {
		double priceForPeriod;
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setPreferredSize(new Dimension(975, 456));
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) ColorConstants.LIGHT_GRAY));
		this.add(panel_1);
		panel_1.setLayout(null);
		
		lblRoomType = new JLabel(roomType.getRoomType());
		lblRoomType.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomType.setForeground(ColorConstants.GRAY);
		lblRoomType.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRoomType.setBounds(12, 29, 203, 54);
		panel_1.add(lblRoomType);
		
		lblRoomsAvaiable = new JLabel("Rooms avaiable: " + roomType.getNumberOfRooms());
		lblRoomsAvaiable.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomsAvaiable.setForeground(new Color(71, 71, 71));
		lblRoomsAvaiable.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblRoomsAvaiable.setBounds(12, 74, 203, 54);
		panel_1.add(lblRoomsAvaiable);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 132, 190, 275);
		panel_1.add(scrollPane);
		
		DefaultTableModel dtm = new DefaultTableModel();
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.PLAIN, 15));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.PLAIN, 17));
		scrollPane.setViewportView(table);
		dtm.addColumn("Room info");
		fillInfoTable(dtm, room);
		
		lblAdultsNum = new JLabel("Number of adults");
		lblAdultsNum.setForeground(ColorConstants.GRAY);
		lblAdultsNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAdultsNum.setBounds(265, 29, 133, 54);
		panel_1.add(lblAdultsNum);
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 10, 1);
		JSpinner spinnerAdults = new JSpinner(sm);
		spinnerAdults.setBounds(410, 37, 58, 43);
		panel_1.add(spinnerAdults);
		
		JLabel lblChildrenUnder = new JLabel("Number of children");
		lblChildrenUnder.setForeground(ColorConstants.GRAY);
		lblChildrenUnder.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblChildrenUnder.setBounds(265, 110, 133, 54);
		panel_1.add(lblChildrenUnder);
		
		JLabel numberOfBads = new JLabel("Number of bads:    " + room.getNumOfBads());
		numberOfBads.setForeground(ColorConstants.GRAY);
		numberOfBads.setFont(new Font("Dialog", Font.PLAIN, 16));
		numberOfBads.setBounds(265, 191, 180, 54);
		panel_1.add(numberOfBads);
		
		SpinnerModel sm1 = new SpinnerNumberModel(0, 0, 10, 1);
		JSpinner spinnerChildren = new JSpinner(sm1);
		spinnerChildren.setBounds(410, 118, 58, 43);
		panel_1.add(spinnerChildren);
		
		priceForPeriod = roomType.getPricePerNight() * period;
		String strPrice =  String.format("%.2f", priceForPeriod);
		JLabel lblPrice = new JLabel(strPrice + "$");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setForeground(ColorConstants.GRAY);
		lblPrice.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPrice.setBounds(527, 39, 133, 54);
		panel_1.add(lblPrice);
		
		separator_3 = new JSeparator();
		separator_3.setBackground(ColorConstants.LIGHT_GRAY);
		separator_3.setOpaque(true);
		separator_3.setBounds(236, 0, 1, 700);
		panel_1.add(separator_3);
		
		separator_4 = new JSeparator();
		separator_4.setOpaque(true);
		separator_4.setBackground(new Color(171, 171, 171));
		separator_4.setBounds(487, 0, 1, 700);
		panel_1.add(separator_4);
		
		separator_5 = new JSeparator();
		separator_5.setOpaque(true);
		separator_5.setBackground(new Color(171, 171, 171));
		separator_5.setBounds(700, 0, 1, 700);
		panel_1.add(separator_5);
		
		separator_6 = new JSeparator();
		separator_6.setOpaque(true);
		separator_6.setBackground(new Color(171, 171, 171));
		separator_6.setBounds(838, 0, 1, 700);
		panel_1.add(separator_6);
		
		lblNumberOfRooms = new JLabel("Num of rooms");
		lblNumberOfRooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumberOfRooms.setForeground(new Color(71, 71, 71));
		lblNumberOfRooms.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblNumberOfRooms.setBounds(709, 13, 117, 54);
		panel_1.add(lblNumberOfRooms);
		
		SpinnerModel sm2 = new SpinnerNumberModel(1, 1, roomType.getNumberOfRooms(), 1);
		sm2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int roomCount = (Integer) sm2.getValue();
				fullPrice = priceForPeriod * roomCount;
				String strPrice =  String.format("%.2f", fullPrice);
				lblPrice.setText(strPrice + "$");
			}
		});
		spinnerRoomCount = new JSpinner(sm2);
		spinnerRoomCount.setBounds(740, 74, 58, 43);
		panel_1.add(spinnerRoomCount);
		
		JButton btnReserve = new JButton("reserve");
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int peopleCount = (Integer) spinnerAdults.getValue() + (Integer) spinnerChildren.getValue();
				int numOfSeclectedRooms = (Integer) sm2.getValue();
				int bedCount = room.getNumOfBads() * numOfSeclectedRooms;
				
				if (peopleCount > bedCount) {
					JOptionPane.showMessageDialog(null, "You selected more guests than number of bads");
				} else {
					int idProperty = propertyWrapper.getProperty().getIdProperty();
					int numOFAdults = (Integer) spinnerAdults.getValue();
					int numOfChilren = (Integer) spinnerChildren.getValue();
					
					Booking booking = new Booking(user.getIdUser(), idProperty, checkIn, checkOut, numOFAdults, numOfChilren, fullPrice);
					BookedRoom bookedRoom = new BookedRoom(roomType.getIdRoomType(), numOfSeclectedRooms, "RESERVED");
					Transaction transaction = new Transaction(user.getIdUser(), idProperty, LocalDate.now(), LocalTime.now());
					transaction.setAmount(fullPrice);
					transaction.setSiteFees(fullPrice);
					
					PropertyWrapper propertyWrapper = createWrapper(booking, bookedRoom, transaction);
					try {
						TransferClass transferClass = ControllerUI.getController().saveBooking(propertyWrapper);
						System.out.println(transferClass.getMessageResponse());
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnReserve.setForeground(new Color(255, 255, 255));
		btnReserve.setBackground(ColorConstants.BLUE);
		btnReserve.setFont(new Font("Dialog", Font.BOLD, 14));
		btnReserve.setBounds(855, 49, 97, 61);
		panel_1.add(btnReserve);
		
		return panel_1;
	}
	
	private PropertyWrapper createWrapper(Booking booking, BookedRoom bookedRoom, Transaction transaction) {
		PropertyWrapper propertyWrapper = new PropertyWrapper();
		Map<Booking, BookedRoom> reservation = new HashMap<>();
		reservation.put(booking, bookedRoom);
		propertyWrapper.setBookings(reservation);
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		propertyWrapper.setTranactions(transactions);
		return propertyWrapper;
	}

	private void fillInfoTable(DefaultTableModel dtm, Room info) {
		dtm.setRowCount(0);
		for (Map.Entry<String, Boolean> entry : info.roomInfoData().entrySet()) {
			Boolean value = entry.getValue();
			if (value) {
				dtm.addRow(new Object[] {entry.getKey()});
			}
		}
	}
}
