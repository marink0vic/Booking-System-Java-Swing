package com.comtrade.view.user.host;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.Address;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.view.user.host.frames.PropertyImagesFrame;
import com.comtrade.view.user.host.frames.RoomFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class HomePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel lblPropertyName;
	private JLabel lblPropertyAddress;
	private JLabel lblSmallImage;
	private DefaultTableModel dtm = new DefaultTableModel();
	private DefaultTableModel dtmInfo = new DefaultTableModel();
	private PropertyWrapper propertyWrapper;
	private User user;
	private List<PropertyImage> propertyImages;
	private RoomType roomType;
	private RoomInfo roomInfo;
	private JPanel gallery = new JPanel();
	private static final int WIDTH = 300;
	private static final int HEIGHT = 220;
	private JTable tableInfo;
	private String roomTypeName = "";
	
	public HomePanel(User user, PropertyWrapper propertyWrapper) {
		this.user = user;
		this.propertyWrapper = propertyWrapper;
		propertyImages = propertyWrapper.getImages();
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		lblPropertyName = new JLabel("");
		lblPropertyName.setForeground(new Color(71, 71, 71));
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 22));
		lblPropertyName.setBorder(null);
		lblPropertyName.setBounds(168, 71, 308, 37);
		this.add(lblPropertyName);
		
		lblPropertyAddress = new JLabel("Propery Address");
		lblPropertyAddress.setForeground(new Color(71, 71, 71));
		lblPropertyAddress.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblPropertyAddress.setBorder(null);
		lblPropertyAddress.setBounds(168, 107, 570, 37);
		this.add(lblPropertyAddress);
		
		lblSmallImage = new JLabel("");
		lblSmallImage.setBorder(null);
		lblSmallImage.setBounds(26, 57, 112, 98);
		this.add(lblSmallImage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 207, 713, 190);
		this.add(scrollPane);
		
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				roomTypeName = (String) dtm.getValueAt(row, 0);
				fillRoomInfoTable();
				table.setSelectionBackground(ColorConstants.RED);
			}
		});
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		Object[] object = {"Room type", "Number of rooms", "Price per night", "Number of bads"};
		dtm.addColumn(object[0]);
		dtm.addColumn(object[1]);
		dtm.addColumn(object[2]);
		dtm.addColumn(object[3]);
		
		JScrollPane scrollPaneInfo = new JScrollPane();
		scrollPaneInfo.setBounds(760, 207, 196, 190);
		this.add(scrollPaneInfo);
		
		tableInfo = new JTable(dtmInfo);
		tableInfo.setForeground(new Color(71, 71, 71));
		tableInfo.setFont(new Font("Dialog", Font.BOLD, 17));
		tableInfo.setRowHeight(30);
		JTableHeader header2 = tableInfo.getTableHeader();
		header2.setFont(new Font("Dialog", Font.BOLD, 20));
	
		scrollPaneInfo.setViewportView(tableInfo);
		dtmInfo.addColumn("Info");
		
		JButton btnImages = new JButton("Manage photos");
		btnImages.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PropertyImagesFrame imagesFrame = new PropertyImagesFrame(HomePanel.this, user, propertyWrapper);
				imagesFrame.setLocationRelativeTo(null);
				imagesFrame.setVisible(true);
			}
		});
		btnImages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnImages.setBounds(754, 561, 129, 68);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnImages.setBounds(756, 563, 125, 63);
			}
		});
		btnImages.setBounds(756, 563, 125, 63);
		btnImages.setForeground(Color.WHITE);
		btnImages.setFont(new Font("Dialog", Font.BOLD, 13));
		btnImages.setBorder(null);
		btnImages.setBackground(new Color(9, 121, 186));
		add(btnImages);
		
		JButton btnAddRoom = new JButton("Add room");
		btnAddRoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RoomFrame roomFrame = new RoomFrame(HomePanel.this, propertyWrapper, "ADD");
				roomFrame.setLocationRelativeTo(null);
				roomFrame.setVisible(true);
			}
		});
		btnAddRoom.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnAddRoom.setBackground(new Color(9, 121, 186));
		btnAddRoom.setBounds(968, 234, 140, 37);
		btnAddRoom.setForeground(Color.WHITE);
		add(btnAddRoom);
		
		JButton btnUpdateRoom = new JButton("Update room");
		btnUpdateRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (roomTypeName.length() > 0) {
					RoomFrame roomFrame = new RoomFrame(HomePanel.this, propertyWrapper, roomType, roomInfo, "UPDATE");
					roomFrame.setLocationRelativeTo(null);
					roomFrame.setVisible(true);	
				} else {
					JOptionPane.showMessageDialog(null, "You have to select the room for update");
				}
			}
		});
		btnUpdateRoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdateRoom.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnUpdateRoom.setBackground(new Color(9, 121, 186));
		btnUpdateRoom.setBounds(968, 305, 140, 37);
		btnUpdateRoom.setForeground(Color.WHITE);
		add(btnUpdateRoom);
		
		addSmallTopIcon();
		addAddressLabelValues();
		fillRoomTypeTable();
		addSmallGallery();
		
	}
	
	public void removeIconFromPanel(int position) {
		gallery.remove(position);
	}
	
	public void addIconToPanel(File file) {
		Icon icon = resizeImage(file, WIDTH, HEIGHT);
		gallery.add(addGalleryPanels(gallery, icon));
	}

	private void addSmallGallery() {
		for (int i = 0; i < propertyImages.size(); i++) {
			File file = new File(propertyImages.get(i).getImage());
			Icon icon = resizeImage(file, WIDTH, HEIGHT);
			gallery.add(addGalleryPanels(gallery, icon));
		}
		JScrollPane scrollPaneImages = new JScrollPane(gallery);
		scrollPaneImages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneImages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneImages.setBounds(26, 429, 690, 201);
		add(scrollPaneImages);
	}

	private JPanel addGalleryPanels(JPanel panel, Icon icon) {
        JPanel newPanel = new JPanel();
        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        lbl.setIcon(icon);
        newPanel.add(lbl);
        return newPanel;
	}
	
	private Icon resizeImage(File selectedFile, int width, int height) {
		ImageIcon myImage = new ImageIcon(selectedFile.getAbsolutePath());
		Image img = myImage.getImage();
		Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	private void addAddressLabelValues() {
		lblPropertyName.setText(propertyWrapper.getProperty().getName());
		Address a = propertyWrapper.getAddress();
		
		String countryName = propertyWrapper.getCountry().getName();
		String addressText = a.getStreet() + " " + a.getNumber() + ", " + a.getZipCode() + " " + a.getCity() + ", " + countryName; 
		lblPropertyAddress.setText(addressText);
	}
	
	public void addSmallTopIcon() {
		String imageUrl = "./resources/images/property-default.png"; 
		if (propertyImages.size() > 0)
			imageUrl = propertyImages.get(0).getImage();
		File f = new File(imageUrl);
		Icon icon = resizeImage(f, lblSmallImage.getWidth(), lblSmallImage.getHeight());
		lblSmallImage.setIcon(icon);
	}

	public void fillRoomTypeTable() {
		dtm.setRowCount(0);
		for (Entry<RoomType, RoomInfo> map : propertyWrapper.getRooms().entrySet()) {
			RoomType rt = map.getKey();
			RoomInfo ri = map.getValue();
			dtm.addRow(new Object[] {rt.getRoomType(), rt.getNumberOfRooms(), rt.getPricePerNight(), ri.getNumOfBads()});
		}
	}
	
	private void fillRoomInfoTable() {
		dtmInfo.setRowCount(0);
		for (Entry<RoomType, RoomInfo> map : propertyWrapper.getRooms().entrySet()) {
			if (map.getKey().getRoomType().equals(roomTypeName)) {
				roomType = map.getKey();
				roomInfo = map.getValue();
				for(Entry<String,Boolean> roomValue : roomInfo.roomInfoData().entrySet()) {
					if (roomValue.getValue()) {
						dtmInfo.addRow(new Object[] {roomValue.getKey()});
					}
				}
			}
		}
	}
}
