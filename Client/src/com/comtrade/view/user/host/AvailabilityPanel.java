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
import java.util.Date;
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

public class AvailabilityPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private Set<RoomType> roomTypes;
	private Map<Booking, List<BookedRoom>> oldBookings;
	private JTable table;
	private JDateChooser chooserCheckIn;
	private JDateChooser chooserCheckOut;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private DefaultTableModel dtm = new DefaultTableModel();
	
	public AvailabilityPanel(Set<RoomType> roomTypes, Map<Booking, List<BookedRoom>> oldBookings) {
		this.roomTypes = roomTypes;
		this.oldBookings = oldBookings;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		JLabel lblCheckIn = new JLabel("CHECK-IN");
		lblCheckIn.setForeground(new Color(71, 71, 71));
		lblCheckIn.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckIn.setBounds(270, 103, 88, 16);
		add(lblCheckIn);
		
		JLabel lblCheckOut = new JLabel("CHECKOUT");
		lblCheckOut.setForeground(new Color(71, 71, 71));
		lblCheckOut.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckOut.setBounds(486, 104, 98, 16);
		add(lblCheckOut);
		
		chooserCheckOut = new JDateChooser();
		chooserCheckOut.setForeground(new Color(71, 71, 71));
		chooserCheckOut.setFont(new Font("Dialog", Font.BOLD, 16));
		chooserCheckOut.setBounds(486, 132, 191, 56);
		add(chooserCheckOut);
		
		chooserCheckIn = new JDateChooser();
		chooserCheckIn.getDateEditor().addPropertyChangeListener( new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				chooserCheckOut.setMinSelectableDate(chooserCheckIn.getDate());
			}
		});
		chooserCheckIn.setMinSelectableDate(new Date());
		chooserCheckIn.setForeground(new Color(71, 71, 71));
		chooserCheckIn.setFont(new Font("Dialog", Font.BOLD, 16));
		chooserCheckIn.setBounds(268, 132, 191, 56);
		add(chooserCheckIn);
		
		JButton button = new JButton("SHOW");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				checkIn = getDate(date, chooserCheckIn);
				checkOut = getDate(date, chooserCheckOut);
				if (checkIn == null || checkOut == null) {
					JOptionPane.showMessageDialog(null, "You have to enter a date");
				} else {
					fillTable();
				}
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Dialog", Font.BOLD, 15));
		button.setBackground(new Color(9, 121, 186));
		button.setBounds(698, 132, 199, 56);
		add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(92, 285, 938, 174);
		add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		
		Object[] object = {"Type of room", "Nuber of avaiable rooms"};
		dtm.addColumn(object[0]);
		dtm.addColumn(object[1]);
	}
	
	private LocalDate getDate(DateFormat date, JDateChooser date_chooser) {
		if(date_chooser.getDate() == null) {
			return null;
		} 
		String strDate = date.format(date_chooser.getDate());
		return LocalDate.parse(strDate);
	}
	
	private void fillTable() {
		dtm.setRowCount(0);
		int freeRooms = 0;
		for (RoomType type : roomTypes) {
			freeRooms = type.getNumberOfRooms();
			for (Map.Entry<Booking, List<BookedRoom>> entry : oldBookings.entrySet()) {
				Booking b = entry.getKey();
				if (b.getCheckOut().isBefore(checkIn) || b.getCheckIn().isAfter(checkOut)) continue;
				for (BookedRoom room : entry.getValue()) {
					if (room.getIdRoomType() == type.getIdRoomType()) {
						freeRooms -= room.getNumberOfRooms();
					}
				}
			}
			if (freeRooms < 0) freeRooms = 0;
			dtm.addRow(new Object[] {type.getRoomType(), freeRooms});
		}
	}

}
