package com.comtrade.view.user.host;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.domain.Address;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.view.user.host.frames.PropertyImagesFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel lblPropertyName;
	private JLabel lblPropertyAddress;
	private JLabel lblSmallImage;
	private DefaultTableModel dtm = new DefaultTableModel();
	private PropertyWrapper propertyOwner;
	private List<PropertyImage> propertyImages;
	private JPanel gallery = new JPanel();
	private static final int WIDTH = 300;
	private static final int HEIGHT = 220;
	
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
		scrollPane.setBounds(25, 207, 749, 190);
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
		
		JButton btnImages = new JButton("Manage photos");
		btnImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int idProperty = propertyOwner.getProperty().getIdProperty();
				PropertyImagesFrame imagesFrame = new PropertyImagesFrame(HomePanel.this, propertyImages, idProperty);
				imagesFrame.setLocationRelativeTo(null);
				imagesFrame.setVisible(true);
			}
		});
		btnImages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnImages.setBounds(647, 485, 129, 84);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnImages.setBounds(649, 487, 125, 79);
			}
		});
		btnImages.setBounds(649, 487, 125, 79);
		btnImages.setForeground(Color.WHITE);
		btnImages.setFont(new Font("Dialog", Font.BOLD, 13));
		btnImages.setBorder(null);
		btnImages.setBackground(new Color(9, 121, 186));
		add(btnImages);
		
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
		scrollPaneImages.setBounds(26, 429, 596, 201);
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
		lblPropertyName.setText(propertyOwner.getProperty().getName());
		Address a = propertyOwner.getAddress();
		
		String countryName = propertyOwner.getCountry().getName();
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

	private void fillRoomTypeTable() {
		dtm.setRowCount(0);
		Set<RoomType> roomType = propertyOwner.getRoom().keySet();
		for (RoomType type : roomType) {
			dtm.addRow(new Object[] {type.getRoomType(), type.getNumberOfRooms(), type.getPricePerNight()});
		}
	}
}
