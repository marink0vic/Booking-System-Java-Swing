package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.util.ImageResize;
import com.comtrade.view.user.regular.property.SelectedPropertyFrame;
import com.toedter.calendar.JDateChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<PropertyWrapper> listOfProperties;
	private User user;
	private JDateChooser dateCheckIn;
	private JDateChooser dateCheckOut;
	private JLabel lblLeftTop;
	private JLabel lblSmallLeftTop;
	private JLabel lblSmallRightTop;
	private JLabel lblRightTop;
	private JLabel lblLeftBottom;
	private JLabel lblSmallLeftBottom;
	private JLabel lblSmallRightBottom;
	private JLabel lblRightBottom;
	

	public HomePagePanel(List<PropertyWrapper> listOfProperties, User user, JDateChooser dateCheckIn, JDateChooser dateCheckOut) {
		this.listOfProperties = listOfProperties;
		this.user = user;
		this.dateCheckIn = dateCheckIn;
		this.dateCheckOut = dateCheckOut;
		initializeComponents();
	}
	
	private void initializeComponents() {
		this.setBounds(252, 204, 975, 728);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		setTopDestinationsLabels();
	}

	private void setTopDestinationsLabels() {
		JLabel lblTopDestinations = new JLabel("Top destinations");
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
		lblLeftTop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				PropertyWrapper pw = listOfProperties.get(0);
				loadSelectedPropertyFrame(pw);
			}
		});
		lblLeftTop.setOpaque(true);
		lblLeftTop.setBorder(null);
		lblLeftTop.setBounds(49, 93, 407, 281);
		lblLeftTop.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
		lblRightTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRightTop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				PropertyWrapper pw = listOfProperties.get(1);
				loadSelectedPropertyFrame(pw);
			}
		});
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
		lblLeftBottom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				PropertyWrapper pw = listOfProperties.get(2);
				loadSelectedPropertyFrame(pw);
			}
		});
		lblLeftBottom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		lblRightBottom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				PropertyWrapper pw = listOfProperties.get(3);
				loadSelectedPropertyFrame(pw);
			}
		});
		lblRightBottom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRightBottom.setOpaque(true);
		lblRightBottom.setBorder(null);
		lblRightBottom.setBounds(509, 418, 407, 281);
		add(lblRightBottom);
		
		JLabel[] imgLabels = {lblLeftTop, lblRightTop, lblLeftBottom, lblRightBottom};
		setDestinationIcons(imgLabels);
		JLabel[] smallTextLabels = {lblSmallLeftTop, lblSmallRightTop, lblSmallLeftBottom, lblSmallRightBottom};
		setSmallLabelsText(smallTextLabels);
	}
	
	private void loadSelectedPropertyFrame(PropertyWrapper pw) {
		LocalDate[] dates = setDates();
		SelectedPropertyFrame reservationFrame = new SelectedPropertyFrame(pw, user, dates[0], dates[1]);
		reservationFrame.setVisible(true);
		reservationFrame.setLocationRelativeTo(null);
	}

	private void setDestinationIcons(JLabel[] labels) {
		String imgPath;
		for (int i = 0; i < labels.length; i++) {
			if (i < listOfProperties.size()) {
				imgPath = listOfProperties.get(i).getImages().get(0).getImage();
				File f = new File(imgPath);
				labels[i].setIcon(ImageResize.resizeImage(f, lblLeftTop.getWidth(), lblLeftTop.getHeight()));
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
	
	private LocalDate[] setDates() {
		LocalDate chcIn = LocalDate.now();
		LocalDate chcOut = chcIn.plusDays(10);
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		if (dateCheckIn.getDate() != null && dateCheckOut.getDate() != null) {
			String in = date.format(dateCheckIn.getDate());
			String out = date.format(dateCheckOut.getDate());
			chcIn = LocalDate.parse(in);
			chcOut = LocalDate.parse(out);
		}
		return new LocalDate[] {chcIn, chcOut};
	}
}
