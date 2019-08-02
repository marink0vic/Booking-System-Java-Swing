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

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.RoomType;
import com.toedter.calendar.JDateChooser;

public class ReservationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Set<RoomType> roomTypes;
	private  Map<Booking, List<BookedRoom>> bookings;
	private static final double PERCENT = 0.1;
	private JDateChooser dateCheckIn;
	private JDateChooser dateCheckOut;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private JLabel lblFullPrice;
	private JTable table;
	
	public ReservationPanel(Set<RoomType> roomTypes, Map<Booking, List<BookedRoom>> bookings) {
		this.roomTypes = roomTypes;
		this.bookings = bookings;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 140, 1063, 388);
		add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		
		Object[] object = {"Guest name", "Check in", "Check out", "Room" , "Price", "Fee", "Booking num"};
		addColumns(object);
		
		JLabel lblCheckin = new JLabel("CHECK-IN");
		lblCheckin.setForeground(ColorConstants.GRAY);
		lblCheckin.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckin.setBounds(234, 23, 88, 16);
		add(lblCheckin);
		
		JLabel lblCheckout = new JLabel("CHECKOUT");
		lblCheckout.setForeground(ColorConstants.GRAY);
		lblCheckout.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckout.setBounds(450, 24, 98, 16);
		add(lblCheckout);
		
		
		
		dateCheckOut = new JDateChooser();
		dateCheckOut.setForeground(ColorConstants.GRAY);
		dateCheckOut.setFont(new Font("Dialog", Font.BOLD, 16));
		dateCheckOut.setBounds(450, 52, 191, 56);
		add(dateCheckOut);
		
		dateCheckIn = new JDateChooser();
		dateCheckIn.getDateEditor().addPropertyChangeListener(
			new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				dateCheckOut.setMinSelectableDate(dateCheckIn.getDate());
			 }
		});
		dateCheckIn.setForeground(ColorConstants.GRAY);
		dateCheckIn.setFont(new Font("Dialog", Font.BOLD, 16));
		dateCheckIn.setBounds(232, 52, 191, 56);
		add(dateCheckIn);
		
		JButton lblShow = new JButton("SHOW");
		lblShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				checkIn = getDate(date, dateCheckIn);
				checkOut = getDate(date, dateCheckOut);
				if (checkIn != null && checkOut != null) {
					if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
						JOptionPane.showMessageDialog(null, "Checkout must be after checkin");
					} else {
						fillTable(checkIn, checkOut);
					}
				} else {
					fillTable(null, null);
				}
				dateCheckOut.setCalendar(null);
				dateCheckIn.setCalendar(null);
			}
		});
		lblShow.setForeground(Color.WHITE);
		lblShow.setFont(new Font("Dialog", Font.BOLD, 15));
		lblShow.setBounds(662, 52, 199, 56);
		lblShow.setBackground(ColorConstants.BLUE);
		add(lblShow);
		
		lblFullPrice = new JLabel("");
		lblFullPrice.setFont(new Font("Dialog", Font.BOLD, 20));
		lblFullPrice.setForeground(ColorConstants.GRAY);
		lblFullPrice.setBounds(33, 562, 367, 50);
		add(lblFullPrice);
		
		fillTable(null, null);
	}
	
	private LocalDate getDate(DateFormat date, JDateChooser date_chooser) {
		if(date_chooser.getDate() == null) {
			return null;
		} 
		String strDate = date.format(date_chooser.getDate());
		return LocalDate.parse(strDate);
	}
	
	private void addColumns(Object[] object) {
		for (int i = 0; i < object.length; i++) {
			dtm.addColumn(object[i]);
		}
	}
	private void fillTable(LocalDate check_in, LocalDate check_out) {
		dtm.setRowCount(0);
		double priceTotal = 0D;
		for (Map.Entry<Booking, List<BookedRoom>> entry : bookings.entrySet()) {
			Booking b = entry.getKey();
			if (check_in != null && check_out != null) {
				if (b.getCheckIn().isBefore(check_in) || b.getCheckOut().isAfter(check_out))
					continue;
			}
			for (BookedRoom br : entry.getValue()) {
				priceTotal += br.getPriceForRoom();
				String roomName = returnRoomName(br);
				dtm.addRow(new Object[] {
						b.getUser().getFirstName() + " " + b.getUser().getLastName(),
						b.getCheckIn(),
						b.getCheckOut(),
						roomName,
						br.getPriceForRoom() + "$",
						String.format("%.2f",br.getPriceForRoom() * PERCENT) + "$",
						b.getIdBooking()
				});
			}
		}
		lblFullPrice.setText("Full price: " + priceTotal + "$");
	}
	
	private String returnRoomName(BookedRoom br) {
		for (RoomType rt : roomTypes) {
			if (rt.getIdRoomType() == br.getIdRoomType())
				return rt.getRoomType();
		}
		return "";
	}

}
