package com.comtrade.view.user.regular;

import java.util.HashMap;
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

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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
	private Map<Integer, String> propertyNames;
	private JLabel lblProfileImage;
	private JTable table;
	String propertyName;
	private DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblNewLabel_1;

	private UserProfileFrame() {
		propertyNames = new HashMap<>();
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
		setBounds(100, 100, 849, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setForeground(ColorConstants.GRAY);
		lblNewLabel.setBounds(342, 36, 273, 103);
		contentPane.add(lblNewLabel);
		
		lblProfileImage = new JLabel();
		lblProfileImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfileImage.setHorizontalTextPosition(SwingConstants.CENTER);
		lblProfileImage.setOpaque(true);
		lblProfileImage.setBounds(204, 36, 117, 103);
		lblProfileImage.setIcon(setProfileImage(user.getProfilePicture()));
		contentPane.add(lblProfileImage);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				propertyName = String.valueOf(comboBox.getSelectedItem());
				fillTable(propertyName);
			}
		});
		comboBox.setFont(new Font("Dialog", Font.BOLD, 18));
		comboBox.setForeground(ColorConstants.GRAY);
		comboBox.setBounds(75, 265, 676, 71);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 381, 676, 278);
		contentPane.add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		
		JLabel lblListOfBookings = new JLabel("LIST OF ALL YOUR BOOKINGS");
		lblListOfBookings.setForeground(ColorConstants.GRAY);
		lblListOfBookings.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblListOfBookings.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfBookings.setBounds(217, 201, 349, 58);
		contentPane.add(lblListOfBookings);
		
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
					User tempUser = new User();
					tempUser.setUsername(user.getUsername());
					tempUser.setIdUser(user.getIdUser());
					tempUser.setProfilePicture(path);
					
					TransferClass transfer = new TransferClass();
					transfer.setClientRequest(tempUser);
					transfer.setDomainType(DomainType.USER);
					transfer.setOperation(Operations.UPDATE);
					ControllerUI.getController().sendToServer(transfer);
					
					lblProfileImage.setIcon(setProfileImage(path));
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file select");
				}
			}
		});
		lblNewLabel_1.setForeground(ColorConstants.BLUE);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_1.setBounds(218, 155, 89, 16);
		contentPane.add(lblNewLabel_1);
		
		Object[] o = {"Check in", "Check out", "Rooms", "Adults", "Children", "Price"};
		dtm.addColumn(o[0]);
		dtm.addColumn(o[1]);
		dtm.addColumn(o[2]);
		dtm.addColumn(o[3]);
		dtm.addColumn(o[4]);
		dtm.addColumn(o[5]);
		
		fillComboBox();
		
		fillTable(propertyName);
	}
	
	public void addNewPropertyName(Integer id, String name) {
		propertyNames.put(id, name);
		fillComboBox();
	}
	
	public void addNewBooking(Booking booking, List<BookedRoom> booked_rooms) {
		myBookings.put(booking, booked_rooms);
	}

	private void fillTable(String property_name) {
		dtm.setRowCount(0);
		
		int idProperty = findId(propertyName);
		for (Map.Entry<Booking, List<BookedRoom>> entry : myBookings.entrySet()) {
			Booking b = entry.getKey();
			if (b.getProperty().getIdProperty() == idProperty) {
				for (BookedRoom br : entry.getValue()) {
					dtm.addRow(new Object[] {
						b.getCheckIn(),
						b.getCheckOut(),
						br.getNumberOfRooms(), 
						br.getNumberOfAdults(), 
						br.getNumberOfChildren(), 
						br.getPriceForRoom() + "$"
					});
				}
			}
		}
		
	}

	private int findId(String property_name) {
		for (Integer i : propertyNames.keySet()) {	
			if (propertyNames.get(i).equals(property_name)) {
				return i;
			}
		}
		return -1;
	}

	private void fillComboBox() {
		comboBox.removeAllItems();
		for (Integer i : propertyNames.keySet()) {
			if (propertyName == null) {
				propertyName = propertyNames.get(i);
			}
			comboBox.addItem(propertyNames.get(i));
		}
	}

	private void setPropertyNames(Set<Booking> key_set) {
		for (Booking booking : key_set) {
			propertyNames.put(booking.getProperty().getIdProperty(), booking.getProperty().getName());
		}
	}
	
	private Icon setProfileImage(String path) {
		File file = new File(path);
		ImageIcon imgIcon = new ImageIcon(file.getPath());
		Image img = imgIcon.getImage();
		Image newImg = img.getScaledInstance(lblProfileImage.getWidth(), lblProfileImage.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
}
