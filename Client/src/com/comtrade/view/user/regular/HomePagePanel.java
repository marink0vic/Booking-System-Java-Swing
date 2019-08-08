package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;

public class HomePagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<PropertyWrapper> listOfProperties;
	private JLabel lblLeftTop;
	private JLabel lblSmallLeftTop;
	private JLabel lblSmallRightTop;
	private JLabel lblRightTop;
	private JLabel lblLeftBottom;
	private JLabel lblSmallLeftBottom;
	private JLabel lblSmallRightBottom;
	private JLabel lblRightBottom;
	//---
	

	public HomePagePanel(List<PropertyWrapper> listOfProperties) {
		this.listOfProperties = listOfProperties;
		initializeComponents();
	}
	
	private void initializeComponents() {
		this.setBounds(252, 204, 975, 728);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		setTopDestinationsLabels();
	}

	private void setTopDestinationsLabels() {
		JLabel lblTopDestinations = new JLabel("Top rated destinations");
		lblTopDestinations.setForeground(new Color(71, 71, 71));
		lblTopDestinations.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTopDestinations.setBounds(49, 27, 430, 43);
		this.add(lblTopDestinations);
		
		lblSmallLeftTop = new JLabel("");
		lblSmallLeftTop.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallLeftTop.setOpaque(true);
		lblSmallLeftTop.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallLeftTop.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallLeftTop.setBackground(new Color(0, 0, 0, 150));
		lblSmallLeftTop.setForeground(new Color(255, 255, 255));
		lblSmallLeftTop.setBounds(49, 294, 407, 80);
		this.add(lblSmallLeftTop);
		
		
		lblLeftTop = new JLabel("");
		lblLeftTop.setOpaque(true);
		lblLeftTop.setBorder(null);
		lblLeftTop.setBounds(49, 93, 407, 281);
		this.add(lblLeftTop);
		
		lblSmallRightTop = new JLabel("");
		lblSmallRightTop.setOpaque(true);
		lblSmallRightTop.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallRightTop.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallRightTop.setForeground(Color.WHITE);
		lblSmallRightTop.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallRightTop.setBackground(new Color(0, 0, 0, 150));
		lblSmallRightTop.setBounds(509, 294, 407, 80);
		add(lblSmallRightTop);
		
		lblRightTop = new JLabel("");
		lblRightTop.setOpaque(true);
		lblRightTop.setBorder(null);
		lblRightTop.setBounds(509, 93, 407, 281);
		add(lblRightTop);
		
		lblSmallLeftBottom = new JLabel("");
		lblSmallLeftBottom.setOpaque(true);
		lblSmallLeftBottom.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallLeftBottom.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallLeftBottom.setForeground(Color.WHITE);
		lblSmallLeftBottom.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallLeftBottom.setBackground(new Color(0, 0, 0, 150));
		lblSmallLeftBottom.setBounds(49, 619, 407, 80);
		add(lblSmallLeftBottom);
		
		lblLeftBottom = new JLabel("");
		lblLeftBottom.setOpaque(true);
		lblLeftBottom.setBorder(null);
		lblLeftBottom.setBounds(49, 418, 407, 281);
		add(lblLeftBottom);
		
		lblSmallRightBottom = new JLabel("");
		lblSmallRightBottom.setOpaque(true);
		lblSmallRightBottom.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallRightBottom.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallRightBottom.setForeground(Color.WHITE);
		lblSmallRightBottom.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallRightBottom.setBackground(new Color(0, 0, 0, 150));
		lblSmallRightBottom.setBounds(509, 619, 407, 80);
		add(lblSmallRightBottom);
		
		lblRightBottom = new JLabel("");
		lblRightBottom.setOpaque(true);
		lblRightBottom.setBorder(null);
		lblRightBottom.setBounds(509, 418, 407, 281);
		add(lblRightBottom);
		
		JLabel[] imgLabels = {lblLeftTop, lblRightTop, lblLeftBottom, lblRightBottom};
		setDestinationIcons(imgLabels);
		JLabel[] smallTextLabels = {lblSmallLeftTop, lblSmallRightTop, lblSmallLeftBottom, lblSmallRightBottom};
		setSmallLabelsText(smallTextLabels);
	}
	
	private void setDestinationIcons(JLabel[] labels) {
		String imgPath;
		for (int i = 0; i < labels.length; i++) {
			if (i < listOfProperties.size()) {
				imgPath = listOfProperties.get(i).getImages().get(0).getImage();
				labels[i].setIcon(resizeImage(imgPath, lblLeftTop.getWidth(), lblLeftTop.getHeight()));
			}
		}
	}
	
	private void setSmallLabelsText(JLabel[] labels) {
		for (int i = 0; i < labels.length; i++) {
			if (i < listOfProperties.size()) {
				PropertyWrapper pw = listOfProperties.get(i);
				String cityName = pw.getAddress().getCity();
				String propertyName = pw.getProperty().getName();
				double avgPrice = avgPropertyPrice(pw.getRooms());
				
				String text = "<html>"
						+cityName + ", "+propertyName + "<br>"
						+"$"+String.format("%.2f", avgPrice) + "/night average"
						+"</html>";
				
				labels[i].setText(text);
			}
		}
	}
	
	private Icon resizeImage(String imgPath, int width, int height) {
		File file = new File(imgPath);
		Image image = new ImageIcon(file.getPath()).getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}
	
	private double avgPropertyPrice(Map<RoomType, Room> room) {
		Set<RoomType> roomType = room.keySet();
		Iterator<RoomType> iterator = roomType.iterator();
		double price = 0.0;
		int roomCount = 0;
		while (iterator.hasNext()) {
			RoomType rType = iterator.next();
			price += rType.getPricePerNight();
			roomCount++;
		}
		return price / roomCount;
	}
}
