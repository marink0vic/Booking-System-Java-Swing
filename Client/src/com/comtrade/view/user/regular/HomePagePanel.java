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

import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;

public class HomePagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<PropertyWrapper> listOfProperties;
	private JLabel lblLeft;
	private JLabel lblMiddle;
	private JLabel lblRight;
	private JLabel lblSmallLeft;
	private JLabel lblSmallMiddle;
	private JLabel lblSmallRight;
	//---
	private JLabel lblRecomended;
	private JLabel lblSmallLeftBottom;
	private JLabel lblSmallMidBottom;
	private JLabel lblSmallRightBottom;
	private JLabel lblBottomLeft;
	private JLabel lblBottomMid;
	private JLabel lblBottomRight;

	public HomePagePanel(List<PropertyWrapper> listOfProperties) {
		this.listOfProperties = listOfProperties;
		initializeComponents();
	}
	
	private void initializeComponents() {
		this.setBounds(252, 204, 975, 728);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		setTopDestinationsLabels();
		setBottomDestinationLabels();
	}

	private void setTopDestinationsLabels() {
		JLabel lblTopDestinations = new JLabel("Top rated destinations");
		lblTopDestinations.setForeground(new Color(71, 71, 71));
		lblTopDestinations.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTopDestinations.setBounds(12, 26, 430, 43);
		this.add(lblTopDestinations);
		
		lblSmallLeft = new JLabel("");
		lblSmallLeft.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallLeft.setOpaque(true);
		lblSmallLeft.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallLeft.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallLeft.setBackground(new Color(0, 0, 0, 150));
		lblSmallLeft.setForeground(new Color(255, 255, 255));
		lblSmallLeft.setBounds(12, 213, 302, 89);
		this.add(lblSmallLeft);
		
		lblSmallMiddle = new JLabel("");
		lblSmallMiddle.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallMiddle.setOpaque(true);
		lblSmallMiddle.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallMiddle.setForeground(Color.WHITE);
		lblSmallMiddle.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallMiddle.setBackground(new Color(0, 0, 0, 150));
		lblSmallMiddle.setBounds(336, 213, 302, 89);
		this.add(lblSmallMiddle);
		
		lblSmallRight = new JLabel("");
		lblSmallRight.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallRight.setOpaque(true);
		lblSmallRight.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallRight.setForeground(Color.WHITE);
		lblSmallRight.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallRight.setBackground(new Color(0, 0, 0, 150));
		lblSmallRight.setBounds(659, 213, 302, 89);
		this.add(lblSmallRight);
		
		lblLeft = new JLabel("");
		lblLeft.setOpaque(true);
		lblLeft.setBorder(null);
		lblLeft.setBounds(12, 92, 302, 210);
		this.add(lblLeft);
		
		lblMiddle = new JLabel("");
		lblMiddle.setOpaque(true);
		lblMiddle.setBorder(null);
		lblMiddle.setBounds(336, 92, 302, 210);
		this.add(lblMiddle);
		
		lblRight = new JLabel("");
		lblRight.setOpaque(true);
		lblRight.setBorder(null);
		lblRight.setBounds(659, 92, 302, 210);
		this.add(lblRight);
		
		JLabel[] labels = {lblLeft, lblMiddle, lblRight};
		setDestinationIcons(labels);
		JLabel[] labelsSmall = {lblSmallLeft, lblSmallMiddle, lblSmallRight};
		setSmallLabelsText(labelsSmall);
	}
	
	private void setBottomDestinationLabels() {
		lblRecomended = new JLabel("Recomended destinations");
		lblRecomended.setForeground(new Color(71, 71, 71));
		lblRecomended.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRecomended.setBounds(12, 340, 430, 43);
		this.add(lblRecomended);
		
		lblSmallLeftBottom = new JLabel("");
		lblSmallLeftBottom.setOpaque(true);
		lblSmallLeftBottom.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallLeftBottom.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallLeftBottom.setForeground(Color.WHITE);
		lblSmallLeftBottom.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallLeftBottom.setBackground(new Color(0, 0, 0, 150));
		lblSmallLeftBottom.setBounds(12, 527, 302, 89);
		this.add(lblSmallLeftBottom);
		
		lblSmallMidBottom = new JLabel("");
		lblSmallMidBottom.setOpaque(true);
		lblSmallMidBottom.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallMidBottom.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallMidBottom.setForeground(Color.WHITE);
		lblSmallMidBottom.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallMidBottom.setBackground(new Color(0, 0, 0, 150));
		lblSmallMidBottom.setBounds(336, 527, 302, 89);
		this.add(lblSmallMidBottom);
		
		lblSmallRightBottom = new JLabel("");
		lblSmallRightBottom.setOpaque(true);
		lblSmallRightBottom.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallRightBottom.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallRightBottom.setForeground(Color.WHITE);
		lblSmallRightBottom.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallRightBottom.setBackground(new Color(0, 0, 0, 150));
		lblSmallRightBottom.setBounds(659, 527, 302, 89);
		this.add(lblSmallRightBottom);
		
		lblBottomLeft = new JLabel("");
		lblBottomLeft.setOpaque(true);
		lblBottomLeft.setBorder(null);
		lblBottomLeft.setBounds(12, 406, 302, 210);
		this.add(lblBottomLeft);
		
		lblBottomMid = new JLabel("");
		lblBottomMid.setOpaque(true);
		lblBottomMid.setBorder(null);
		lblBottomMid.setBounds(336, 406, 302, 210);
		this.add(lblBottomMid);
		
		lblBottomRight = new JLabel("");
		lblBottomRight.setOpaque(true);
		lblBottomRight.setBorder(null);
		lblBottomRight.setBounds(659, 406, 302, 210);
		this.add(lblBottomRight);
		
		JLabel[] labels = {lblBottomLeft, lblBottomMid, lblBottomRight};
		setDestinationIcons(labels);
		JLabel[] labelsSmall = {lblSmallLeftBottom, lblSmallMidBottom, lblSmallRightBottom};
		setSmallLabelsText(labelsSmall);
	}
	
	private void setDestinationIcons(JLabel[] labels) {
		String imgPath;
		for (int i = 0; i < labels.length; i++) {
			imgPath = listOfProperties.get(i).getImages().get(0).getImage();
			labels[i].setIcon(resizeImage(imgPath, lblLeft.getWidth(), lblLeft.getHeight()));
		}
	}
	
	private void setSmallLabelsText(JLabel[] labels) {
		for (int i = 0; i < labels.length; i++) {
			PropertyWrapper pw = listOfProperties.get(i);
			String cityName = pw.getAddress().getCity();
			String propertyName = pw.getProperty().getName();
			double avgPrice = avgPropertyPrice(pw.getRoom());
			
			String text = "<html>"
			+cityName + ", "+propertyName + "<br>"
			+"$"+String.format("%.2f", avgPrice) + "/night average"
			+"</html>";
			
			labels[i].setText(text);
		}
	}
	
	private Icon resizeImage(String imgPath, int width, int height) {
		File file = new File(imgPath);
		Image image = new ImageIcon(file.getPath()).getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}
	
	private double avgPropertyPrice(Map<RoomType, RoomInfo> room) {
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
