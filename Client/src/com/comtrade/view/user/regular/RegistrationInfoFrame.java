package com.comtrade.view.user.regular;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RegistrationInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Entry<Booking, List<BookedRoom>> bookings;
	private Set<RoomType> rooms;

	public RegistrationInfoFrame(PropertyWrapper wrapper, Set<RoomType> rooms) {
		this.bookings = wrapper.getBookings().entrySet().iterator().next();
		this.rooms = rooms;
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 580);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Booking information");
		lblNewLabel.setForeground(ColorConstants.GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 19));
		lblNewLabel.setBounds(31, 27, 365, 56);
		contentPane.add(lblNewLabel);
		
		JLabel lblCheckIn = new JLabel("Check in: " + bookings.getKey().getCheckIn());
		lblCheckIn.setForeground(new Color(71, 71, 71));
		lblCheckIn.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCheckIn.setBounds(31, 106, 175, 56);
		contentPane.add(lblCheckIn);
		
		JLabel lblCheckOut = new JLabel("Check out: " + bookings.getKey().getCheckOut());
		lblCheckOut.setForeground(new Color(71, 71, 71));
		lblCheckOut.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCheckOut.setBounds(218, 106, 178, 56);
		contentPane.add(lblCheckOut);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 175, 408, 226);
		contentPane.add(scrollPane);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		Object[] o = {"Room", "Room count", "Adults", "Children", "Price"};
		dtm.addColumn(o[0]);
		dtm.addColumn(o[1]);
		dtm.addColumn(o[2]);
		dtm.addColumn(o[3]);
		dtm.addColumn(o[4]);
		
		fillTable();
		
		JLabel lblFinalPrice = new JLabel("Final price: " + bookings.getKey().getPriceForStay() + "$");
		lblFinalPrice.setForeground(new Color(71, 71, 71));
		lblFinalPrice.setFont(new Font("Dialog", Font.BOLD, 16));
		lblFinalPrice.setBounds(12, 414, 229, 56);
		contentPane.add(lblFinalPrice);
		
	}

	private void fillTable() {
		for (RoomType rt : rooms) {
			for (BookedRoom br : bookings.getValue()) {
				if (rt.getIdRoomType() == br.getIdRoomType()) {
					dtm.addRow(new Object[] {rt.getRoomType(), br.getNumberOfRooms(), br.getNumberOfAdults(), br.getNumberOfChildren(), br.getPriceForRoom()});
				}
			}
		}
	}
}
