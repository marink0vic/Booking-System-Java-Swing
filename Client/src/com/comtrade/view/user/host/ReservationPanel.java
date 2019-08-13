package com.comtrade.view.user.host;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReservationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm = new DefaultTableModel();
	private DefaultTableModel dtm2 = new DefaultTableModel();
	private Set<RoomType> roomTypes;
	private  Map<Booking, List<BookedRoom>> oldBookings;
	private  Map<Booking, List<BookedRoom>> newBookings;
	private JDateChooser dateCheckIn;
	private JDateChooser dateCheckOut;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private JLabel lblFullPrice;
	private JLabel lblFullPrice2;
	private JTable table;
	private JTable table2;
	private JLayeredPane layeredPane;
	private JPanel OldReservationPanel;
	private JLabel lblAllReservations;
	private JLabel lblNewReservations;
	private JPanel NewReservationPanel;
	private JButton lblAddNewRes;
	private JLabel lblNewRes;
	private JLabel smallOwnerLabel;
	
	public ReservationPanel(
			Set<RoomType> roomTypes, 
			Map<Booking, List<BookedRoom>> oldBookings, 
			Map<Booking, List<BookedRoom>> newBookings,
			JLabel smallOwnerLabel) 
	{
		this.roomTypes = roomTypes;
		this.oldBookings = oldBookings;
		this.newBookings = newBookings;
		this.smallOwnerLabel = smallOwnerLabel;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(12, 69, 1108, 585);
		add(layeredPane);
		
		lblAllReservations = new JLabel("ALL RESERVATIONS");
		lblAllReservations.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAllReservations.setForeground(ColorConstants.BLUE);
				lblNewReservations.setForeground(ColorConstants.GRAY);
				switchPanel(OldReservationPanel);
			}
		});
		lblAllReservations.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAllReservations.setFont(new Font("Dialog", Font.BOLD, 17));
		lblAllReservations.setForeground(ColorConstants.BLUE);
		lblAllReservations.setHorizontalAlignment(SwingConstants.CENTER);
		lblAllReservations.setBounds(315, 9, 212, 47);
		add(lblAllReservations);
		
		lblNewReservations = new JLabel("NEW RESERVATIONS");
		lblNewReservations.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblNewReservations.setForeground(ColorConstants.BLUE);
				lblAllReservations.setForeground(ColorConstants.GRAY);
				switchPanel(NewReservationPanel);
			}
		});
		lblNewReservations.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewReservations.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewReservations.setForeground(ColorConstants.GRAY);
		lblNewReservations.setFont(new Font("Dialog", Font.BOLD, 17));
		lblNewReservations.setBounds(593, 9, 212, 47);
		add(lblNewReservations);
		
		JSeparator separator = new JSeparator();
		separator.setOpaque(true);
		separator.setBackground(ColorConstants.LIGHT_GRAY);
		separator.setBounds(32, 65, 1065, 2);
		add(separator);
		
		OldReservationPanel = new JPanel();
		OldReservationPanel.setBackground(new Color(255, 255, 255));
		OldReservationPanel.setBounds(0, 0, 1108, 585);
		OldReservationPanel.setLayout(null);
		layeredPane.add(OldReservationPanel,"fdsfsdff");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 154, 1063, 342);
		OldReservationPanel.add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		Object[] object = {"Guest name", "Check in", "Check out", "Room" , "Price", "Booking num"};
		addColumns(dtm, object);
		
		JLabel lblCheckin = new JLabel("CHECK-IN");
		lblCheckin.setBounds(225, 29, 88, 16);
		lblCheckin.setForeground(ColorConstants.GRAY);
		lblCheckin.setFont(new Font("Dialog", Font.BOLD, 15));
		OldReservationPanel.add(lblCheckin);
		
		JLabel lblCheckout = new JLabel("CHECKOUT");
		lblCheckout.setBounds(441, 30, 98, 16);
		lblCheckout.setForeground(ColorConstants.GRAY);
		lblCheckout.setFont(new Font("Dialog", Font.BOLD, 15));
		OldReservationPanel.add(lblCheckout);

		dateCheckOut = new JDateChooser();
		dateCheckOut.setBounds(441, 58, 191, 56);
		dateCheckOut.setForeground(ColorConstants.GRAY);
		dateCheckOut.setFont(new Font("Dialog", Font.BOLD, 16));
		OldReservationPanel.add(dateCheckOut);
		
		dateCheckIn = new JDateChooser();
		dateCheckIn.setBounds(223, 58, 191, 56);
		dateCheckIn.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				dateCheckOut.setMinSelectableDate(dateCheckIn.getDate());
			 }
		});
		dateCheckIn.setForeground(ColorConstants.GRAY);
		dateCheckIn.setFont(new Font("Dialog", Font.BOLD, 16));
		OldReservationPanel.add(dateCheckIn);
		
		JButton lblShow = new JButton("SHOW");
		lblShow.setBounds(653, 58, 199, 56);
		OldReservationPanel.add(lblShow);
		lblShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				checkIn = getDate(date, dateCheckIn);
				checkOut = getDate(date, dateCheckOut);
				if (checkIn != null && checkOut != null) {
					if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
						JOptionPane.showMessageDialog(null, "Checkout must be after checkin");
					} else {
						fillTable();
					}
				} else {
					fillTable();
				}
				dateCheckOut.setCalendar(null);
				dateCheckIn.setCalendar(null);
			}
		});
		lblShow.setForeground(Color.WHITE);
		lblShow.setFont(new Font("Dialog", Font.BOLD, 15));
		lblShow.setBackground(ColorConstants.BLUE);
		
		lblFullPrice = new JLabel("");
		lblFullPrice.setBounds(20, 522, 483, 50);
		OldReservationPanel.add(lblFullPrice);
		lblFullPrice.setFont(new Font("Dialog", Font.BOLD, 20));
		lblFullPrice.setForeground(ColorConstants.GRAY);
		
		NewReservationPanel = new JPanel();
		NewReservationPanel.setBackground(new Color(255, 255, 255));
		NewReservationPanel.setBounds(0, 0, 1108, 585);
		NewReservationPanel.setLayout(null);
		layeredPane.add(NewReservationPanel);
		
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(20, 120, 1063, 342);
		NewReservationPanel.add(scrollPane2);
		
		table2 = new JTable(dtm2);
		table2.setForeground(ColorConstants.GRAY);
		table2.setFont(new Font("Dialog", Font.BOLD, 17));
		table2.setRowHeight(30);
		JTableHeader header2 = table2.getTableHeader();
		header2.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane2.setViewportView(table2);
		Object[] object2 = {"Guest name", "Check in", "Check out", "Room" , "Price", "Booking num"};
		addColumns(dtm2, object2);
		
		lblAddNewRes = new JButton("CONFIRM NEW BOOKINGS");
		lblAddNewRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (newBookings.size() > 0) {
					PropertyWrapper pw = sendUpdatedReservationToServer();
					oldBookings.putAll(pw.getBookings());
					newBookings = new HashMap<>();
					
					updateFlagLabels();
					fillTable();
					fillNewBookingTable();
				}
			}
		});
		lblAddNewRes.setForeground(new Color(255, 255, 255));
		lblAddNewRes.setBackground(ColorConstants.RED);
		lblAddNewRes.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAddNewRes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAddNewRes.setBounds(389, 502, 335, 57);
		NewReservationPanel.add(lblAddNewRes);
		
		lblFullPrice2 = new JLabel("");
		lblFullPrice2.setBounds(20, 522, 367, 50);
		NewReservationPanel.add(lblFullPrice2);
		lblFullPrice2.setFont(new Font("Dialog", Font.BOLD, 20));
		lblFullPrice2.setForeground(ColorConstants.GRAY);
		
		lblNewRes = new JLabel("");
		lblNewRes.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewRes.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewRes.setOpaque(true);
		lblNewRes.setForeground(new Color(255, 255, 255));
		lblNewRes.setBackground(Color.WHITE);
		lblNewRes.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewRes.setBounds(820, 20, 39, 28);
		if (newBookings.size() > 0) {
			lblNewRes.setBackground(ColorConstants.RED);
			lblNewRes.setText(String.valueOf(newBookings.size()));
		}
		add(lblNewRes);
		
		fillTable();
		fillNewBookingTable();
		sort(dtm, table);
		sort(dtm2, table2);
	}

	private void sort(DefaultTableModel d, JTable t) {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(d);
		t.setRowSorter(sorter);
	}

	protected void updateFlagLabels() {
		smallOwnerLabel.setText("");
		smallOwnerLabel.setBackground(null);
		lblNewRes.setText("");
		lblNewRes.setBackground(Color.WHITE);
	}

	private PropertyWrapper sendUpdatedReservationToServer() {
		TransferClass transfer = new TransferClass();
		transfer.setDomainType(DomainType.BOOKING);
		transfer.setOperation(Operations.UPDATE);
		PropertyWrapper pw = new PropertyWrapper();
		pw.setBookings(newBookings);
		transfer.setClientRequest(pw);
		ControllerUI.getController().sendToServer(transfer);
		
		pw = ControllerUI.getController().getPropertyWrapper();
		return pw;
	}

	private void fillNewBookingTable() {
		dtm2.setRowCount(0);
		double priceTotal = 0D;
		for (Map.Entry<Booking, List<BookedRoom>> entry : newBookings.entrySet()) {
			Booking b = entry.getKey();
			priceTotal += addBookedRooms(b, entry.getValue(), dtm2);
		}
		String price = String.format("%.2f", priceTotal);
		lblFullPrice2.setText("Amount paid for this period: " + price + "$");
	}
	
	private void fillTable() {
		dtm.setRowCount(0);
		double priceTotal = 0D;
		
		if (checkIn != null && checkOut != null) {
			for (Map.Entry<Booking, List<BookedRoom>> entry : oldBookings.entrySet()) {
				Booking b = entry.getKey();
				if (b.getCheckIn().isBefore(checkIn) || b.getCheckOut().isAfter(checkOut)) continue;
				priceTotal += addBookedRooms(b, entry.getValue(), dtm);
			}
		} else {
			for (Map.Entry<Booking, List<BookedRoom>> entry : oldBookings.entrySet()) {
				Booking b = entry.getKey();
				priceTotal += addBookedRooms(b, entry.getValue(), dtm);
			}
		}
		
		String price = String.format("%.2f", priceTotal);
		lblFullPrice.setText("Amount paid for this period: " + price + "$");
	}
	
	private double addBookedRooms(Booking b, List<BookedRoom> list, DefaultTableModel default_model) {
		double priceTotal = 0D;
		for (BookedRoom br : list) {
			priceTotal += br.getPriceForRoom();
			String roomName = returnRoomName(br);
			default_model.addRow(new Object[] {
					b.getUser().getFirstName() + " " + b.getUser().getLastName(),
					b.getCheckIn(),
					b.getCheckOut(),
					roomName,
					String.format("%.2f",br.getPriceForRoom()) + "$",
					b.getIdBooking()
			});
		}
		return priceTotal;
	}

	private LocalDate getDate(DateFormat date, JDateChooser date_chooser) {
		if(date_chooser.getDate() == null) {
			return null;
		} 
		String strDate = date.format(date_chooser.getDate());
		return LocalDate.parse(strDate);
	}
	
	private void addColumns(DefaultTableModel dt_model, Object[] object) {
		for (int i = 0; i < object.length; i++) {
			dt_model.addColumn(object[i]);
		}
	}
	
	private String returnRoomName(BookedRoom br) {
		for (RoomType rt : roomTypes) {
			if (rt.getIdRoomType() == br.getIdRoomType())
				return rt.getRoomType();
		}
		return "";
	}
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	public void addNewBookings(Map<Booking, List<BookedRoom>> temp) {
		newBookings.putAll(temp);
		lblNewRes.setBackground(ColorConstants.RED);
		lblNewRes.setText(String.valueOf(newBookings.size()));
		smallOwnerLabel.setBackground(ColorConstants.RED);
		smallOwnerLabel.setText(String.valueOf(newBookings.size()));
		fillNewBookingTable();
	}
}
