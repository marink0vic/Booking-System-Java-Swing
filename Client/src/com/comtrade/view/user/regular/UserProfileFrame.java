package com.comtrade.view.user.regular;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.User;
import com.comtrade.dto.UserWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.util.ImageResize;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class UserProfileFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static UserProfileFrame userProfile;
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private User user;
	private Map<Booking, List<BookedRoom>> myBookings;
	private Map<Integer, String> propertyNamesAccepted;
	private Map<Integer, String> propertyNamesPending;
	private JLabel lblProfileImage;
	private JTable table;
	private String propertyName;
	private String status = "ACCEPTED";
	private DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblNewLabel_1;
	private JLabel lblPendingBookings;
	private JLabel lblAcceptedBookings;
	private JLabel lblMessageInfo;
	
	private UserProfileFrame() {
		propertyNamesAccepted = new HashMap<>();
		propertyNamesPending = new HashMap<>();
	}
	
	public void setBookings(UserWrapper userWrapper) {
		this.user = userWrapper.getUser();
		this.myBookings = userWrapper.getBookings();
		setPropertyNames(myBookings.keySet());
	}
	
	public static UserProfileFrame getFrame() {
		if (userProfile == null) {
			userProfile = new UserProfileFrame();
		}
		return userProfile;
	}

	public void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setForeground(ColorConstants.GRAY);
		lblNewLabel.setBounds(412, 36, 273, 103);
		contentPane.add(lblNewLabel);
		
		lblProfileImage = new JLabel();
		lblProfileImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfileImage.setHorizontalTextPosition(SwingConstants.CENTER);
		lblProfileImage.setOpaque(true);
		lblProfileImage.setBounds(274, 36, 117, 103);
		File f = new File(user.getProfilePicture());
		Icon icon = ImageResize.resizeImage(f, lblProfileImage.getWidth(), lblProfileImage.getHeight());
		lblProfileImage.setIcon(icon);
		contentPane.add(lblProfileImage);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				propertyName = String.valueOf(comboBox.getSelectedItem());
				Map<Integer, String> propertyNames = findCorrectMap(propertyName);
				fillTable(propertyName, propertyNames);
			}
		});
		comboBox.setFont(new Font("Dialog", Font.BOLD, 18));
		comboBox.setForeground(ColorConstants.GRAY);
		comboBox.setBounds(75, 265, 800, 71);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 381, 800, 278);
		contentPane.add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		
		lblAcceptedBookings = new JLabel("ACCEPTED BOOKINGS");
		lblAcceptedBookings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAcceptedBookings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				fillComboBox(propertyNamesAccepted);
				lblAcceptedBookings.setForeground(ColorConstants.BLUE);
				lblPendingBookings.setForeground(ColorConstants.GRAY);
				lblMessageInfo.setText("");
				propertyName = String.valueOf(comboBox.getSelectedItem());
				status = "ACCEPTED";
				fillTable(propertyName, propertyNamesAccepted);
			}
		});
		lblAcceptedBookings.setForeground(ColorConstants.BLUE);
		lblAcceptedBookings.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAcceptedBookings.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcceptedBookings.setBounds(129, 194, 349, 58);
		contentPane.add(lblAcceptedBookings);
		
		lblPendingBookings = new JLabel("PENDING BOOKINGS");
		lblPendingBookings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblPendingBookings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				fillComboBox(propertyNamesPending);
				lblPendingBookings.setForeground(ColorConstants.BLUE);
				lblAcceptedBookings.setForeground(ColorConstants.GRAY);
				propertyName = String.valueOf(comboBox.getSelectedItem());
				status = "PENDING";
				fillTable(propertyName, propertyNamesPending);
			}
		});
		lblPendingBookings.setHorizontalAlignment(SwingConstants.CENTER);
		lblPendingBookings.setForeground(ColorConstants.GRAY);
		lblPendingBookings.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPendingBookings.setBounds(473, 194, 349, 58);
		contentPane.add(lblPendingBookings);
		
		lblNewLabel_1 = new JLabel("Upload photo");
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File("C:\\Users\\marko\\Desktop\\users"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					user.setProfilePicture(path);
					
					User tempUser = prepareUserDataForDB(path);
					sendProfilePicToServer(tempUser);
					
					File f = new File(path);
					Icon icon = ImageResize.resizeImage(f, lblProfileImage.getWidth(), lblProfileImage.getHeight());
					lblProfileImage.setIcon(icon);
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file select");
				}
			}
		});
		lblNewLabel_1.setForeground(ColorConstants.BLUE);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_1.setBounds(288, 155, 89, 16);
		contentPane.add(lblNewLabel_1);
		
		lblMessageInfo = new JLabel("");
		lblMessageInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessageInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblMessageInfo.setForeground(ColorConstants.RED);
		lblMessageInfo.setBounds(262, 687, 436, 38);
		contentPane.add(lblMessageInfo);
		
		Object[] o = {"Check in", "Check out", "Rooms", "Adults", "Children", "Price", "Booking ID"};
		dtm.addColumn(o[0]);
		dtm.addColumn(o[1]);
		dtm.addColumn(o[2]);
		dtm.addColumn(o[3]);
		dtm.addColumn(o[4]);
		dtm.addColumn(o[5]);
		dtm.addColumn(o[6]);
		
		fillComboBox(propertyNamesAccepted);
		fillTable(propertyName, propertyNamesAccepted);
	}

	protected Map<Integer, String> findCorrectMap(String property_name) {
		for (Map.Entry<Integer, String> entry : propertyNamesAccepted.entrySet()) {
			if (property_name.equals(entry.getValue())) return propertyNamesAccepted;
		}
		return propertyNamesPending;
	}

	public void addNewPropertyName(Integer id, String name) {
		propertyNamesPending.put(id, name);
	}
	
	public void addNewBooking(Booking booking, List<BookedRoom> booked_rooms) {
		myBookings.put(booking, booked_rooms);
	}

	private void fillTable(String property_name, Map<Integer, String> booking_names) {
		dtm.setRowCount(0);
		
		int idProperty = findId(propertyName, booking_names);
		for (Map.Entry<Booking, List<BookedRoom>> entry : myBookings.entrySet()) {
			Booking b = entry.getKey();
			if (b.getProperty().getIdProperty() == idProperty && b.getStatus().equals(status)) {
				for (BookedRoom br : entry.getValue()) {
					dtm.addRow(new Object[] {
						b.getCheckIn(),
						b.getCheckOut(),
						br.getNumberOfRooms(), 
						br.getNumberOfAdults(), 
						br.getNumberOfChildren(), 
						br.getPriceForRoom() + "$",
						b.getIdBooking()
					});
				}
			}
		}
	}

	private int findId(String property_name, Map<Integer, String> booking_names) {
		for (Integer i : booking_names.keySet()) {	
			if (booking_names.get(i).equals(property_name)) {
				return i;
			}
		}
		return -1;
	}

	private void fillComboBox(Map<Integer, String> bookings) {
		comboBox.removeAllItems();
		for (Integer i : bookings.keySet()) {
			if (propertyName == null) {
				propertyName = bookings.get(i);
			}
			comboBox.addItem(bookings.get(i));
		}
	}

	private void setPropertyNames(Set<Booking> key_set) {
		for (Booking booking : key_set) {
			if (booking.getStatus().equals("PENDING")) {
				propertyNamesPending.put(booking.getProperty().getIdProperty(), booking.getProperty().getName());
			} else {
				propertyNamesAccepted.put(booking.getProperty().getIdProperty(), booking.getProperty().getName());
			}
		}
	}
	
	private User prepareUserDataForDB(String path) {
		User tempUser = new User();
		tempUser.setUsername(user.getUsername());
		tempUser.setIdUser(user.getIdUser());
		tempUser.setProfilePicture(path);
		return tempUser;
	}
	
	private void sendProfilePicToServer(User temp_user) {
		TransferClass transfer = new TransferClass();
		transfer.setClientRequest(temp_user);
		transfer.setDomainType(DomainType.USER);
		transfer.setOperation(Operations.UPDATE);
		ControllerUI.getController().sendToServer(transfer);
	}

	public void updateBookings(List<Booking> acceptedBookings) {
		for (Booking booking : acceptedBookings) {
			for (Booking b : myBookings.keySet()) {
				if (booking.getIdBooking() == b.getIdBooking()) {
					b.setStatus("ACCEPTED");
					break;
				}
			}
		}
		updateNames(acceptedBookings);
	}

	private void updateNames(List<Booking> acceptedBookings) {
		for (Booking booking : acceptedBookings) {
			Iterator<Map.Entry<Integer, String>> iterator = propertyNamesPending.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, String> entry = iterator.next();
				if (entry.getKey() == booking.getProperty().getIdProperty()) {
					propertyNamesAccepted.put(entry.getKey(), entry.getValue());
					iterator.remove();
				}
			}
		}
		lblMessageInfo.setText("YOU HAVE NEW ACCEPTED BOOKINGS");
		fillComboBox(propertyNamesAccepted);
		fillTable(propertyName, propertyNamesAccepted);
	}
}
