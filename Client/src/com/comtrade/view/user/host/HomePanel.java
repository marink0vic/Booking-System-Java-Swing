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
import javax.swing.table.JTableHeader;

import com.comtrade.domain.Address;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class HomePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel lblPropertyName;
	private JLabel lblPropertyAddress;
	private JLabel lblSmallImage;
	private DefaultTableModel dtm = new DefaultTableModel();
	private PropertyWrapper propertyOwner;
	private List<PropertyImage> propertyImages;
	//--galery images
	private JLabel lblImage1;
	private JLabel lblImage2;
	private JLabel lblImage3;
	
	public HomePanel(PropertyWrapper propertyOwner) {
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
		scrollPane.setBounds(25, 207, 749, 224);
		this.add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(new Color(71, 71, 71));
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		Object[] object = {"Room type", "Number of rooms", "Price per night"};
		dtm.addColumn(object[0]);
		dtm.addColumn(object[1]);
		dtm.addColumn(object[2]);
		
		lblImage1 = new JLabel("");
		lblImage1.setBorder(null);
		lblImage1.setBounds(26, 467, 152, 152);
		this.add(lblImage1);
		
		lblImage2 = new JLabel("");
		lblImage2.setBorder(null);
		lblImage2.setBounds(227, 467, 152, 152);
		this.add(lblImage2);
		
		lblImage3 = new JLabel("");
		lblImage3.setBorder(null);
		lblImage3.setBounds(430, 467, 152, 152);
		this.add(lblImage3);
		
		JButton btnImages = new JButton("Add Images");
		btnImages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnImages.setBounds(647, 503, 129, 84);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnImages.setBounds(649, 505, 125, 79);
			}
		});
		btnImages.setBounds(649, 505, 125, 79);
		btnImages.setForeground(Color.WHITE);
		btnImages.setFont(new Font("Dialog", Font.BOLD, 15));
		btnImages.setBorder(null);
		btnImages.setBackground(new Color(9, 121, 186));
		add(btnImages);
		
		addLabelsValues();
		fillRoomTypeTable();
		fillGallerySlider();
	}
	
	private void fillGallerySlider() {
		
		if (propertyImages.get(0) != null) {
			lblImage1.setIcon(setImageIcon(propertyImages.get(0).getImage()));
		}
		if (propertyImages.get(1) != null) {
			lblImage2.setIcon(setImageIcon(propertyImages.get(1).getImage()));
		}
		if (propertyImages.get(2) != null) {
			lblImage3.setIcon(setImageIcon(propertyImages.get(2).getImage()));
		}
	}
	
	private Icon setImageIcon(String image) {
		File f = new File(image);
		Icon icon = resizeImage(f, lblImage1.getWidth(), lblImage1.getHeight());
		return icon;
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
