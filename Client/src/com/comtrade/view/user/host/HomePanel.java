package com.comtrade.view.user.host;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.comtrade.domain.Address;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.UserWrapper;

public class HomePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel lblPropertyName;
	private JLabel lblPropertyAddress;
	private JLabel lblSmallImage;
	private DefaultTableModel dtm = new DefaultTableModel();
	private UserWrapper propertyOwner;
	private List<PropertyImage> propertyImages;
	
	public HomePanel(UserWrapper propertyOwner) {
		this.propertyOwner = propertyOwner;
		propertyImages = propertyOwner.getImages();
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		lblPropertyName = new JLabel("");
		lblPropertyName.setForeground(new Color(71, 71, 71));
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPropertyName.setBorder(null);
		lblPropertyName.setBounds(168, 71, 308, 37);
		this.add(lblPropertyName);
		
		lblPropertyAddress = new JLabel("Propery Address");
		lblPropertyAddress.setForeground(new Color(71, 71, 71));
		lblPropertyAddress.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblPropertyAddress.setBorder(null);
		lblPropertyAddress.setBounds(168, 107, 570, 37);
		this.add(lblPropertyAddress);
		
		lblSmallImage = new JLabel("");
		lblSmallImage.setBorder(null);
		lblSmallImage.setBounds(26, 57, 112, 98);
		this.add(lblSmallImage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 207, 670, 224);
		this.add(scrollPane);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		Object[] object = {"Room type", "Number of rooms", "Price per night"};
		dtm.addColumn(object[0]);
		dtm.addColumn(object[1]);
		dtm.addColumn(object[2]);
		
		JLabel arrowLeft = new JLabel("");
		arrowLeft.setHorizontalAlignment(SwingConstants.LEFT);
		arrowLeft.setBorder(null);
		arrowLeft.setBounds(25, 513, 56, 44);
		Icon iconLeftArrow = new ImageIcon("./resources/icons/arrow-left.png");
		arrowLeft.setIcon(iconLeftArrow);
		this.add(arrowLeft);
		
		JLabel arrowRight = new JLabel("");
		arrowRight.setHorizontalAlignment(SwingConstants.RIGHT);
		arrowRight.setBorder(null);
		arrowRight.setBounds(639, 513, 56, 44);
		Icon iconRightArrow = new ImageIcon("./resources/icons/arrow-right.png");
		arrowRight.setIcon(iconRightArrow);
		this.add(arrowRight);
		
		JLabel lblImage1 = new JLabel("");
		lblImage1.setBorder(null);
		lblImage1.setBounds(93, 467, 152, 152);
		this.add(lblImage1);
		
		JLabel lblImage2 = new JLabel("");
		lblImage2.setBorder(null);
		lblImage2.setBounds(281, 467, 152, 152);
		this.add(lblImage2);
		
		JLabel lblImage3 = new JLabel("");
		lblImage3.setBorder(null);
		lblImage3.setBounds(475, 467, 152, 152);
		this.add(lblImage3);
		
		addLabelsValues();
		fillRoomTypeTable();
	}
	
	private void addLabelsValues() {
		lblPropertyName.setText(propertyOwner.getProperty().getName());
		Address a = propertyOwner.getAddress();
		String countryName = propertyOwner.getCountry().getName();
		String addressText = a.getStreet() + " " + a.getNumber() + ", " + a.getZipCode() + " " + a.getCity() + ", " + countryName; 
		lblPropertyAddress.setText(addressText);
		String imageUrl = propertyImages.get(0).getImage();
		File f = new File(imageUrl);
		Icon icon = resizeImage(f, lblSmallImage.getWidth(), lblSmallImage.getHeight());
		lblSmallImage.setIcon(icon);
	}
	
	private Icon resizeImage(File selectedFile, int width, int height) {
		ImageIcon myImage = new ImageIcon(selectedFile.getAbsolutePath());
		Image img = myImage.getImage();
		Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	private void fillRoomTypeTable() {
		dtm.setRowCount(0);
		Set<RoomType> roomType = propertyOwner.getRoom().keySet();
		for (RoomType type : roomType) {
			dtm.addRow(new Object[] {type.getRoomType(), type.getNumberOfRooms(), type.getPricePerNight()});
		}
	}

}
