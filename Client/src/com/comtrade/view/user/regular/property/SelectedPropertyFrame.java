package com.comtrade.view.user.regular.property;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.view.user.regular.HeaderPanel;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;


public class SelectedPropertyFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HeaderPanel headerPanel;
	private JLayeredPane layeredPane;
	private PropertyInfoPanel propertyInfoPanel;
	private RoomsPricesPanel roomPricesPanel;
	//--
	private PropertyWrapper propertyWrapper;
	private JLabel lblInfo;
	private JLabel lblRoomPrices;
	private JLabel lblGuestReviews;
	//---
	private Map<RoomType, Room> rooms;

	
	public SelectedPropertyFrame(PropertyWrapper propertyWrapper) {
		this.propertyWrapper = propertyWrapper;
		this.rooms = propertyWrapper.getRooms();
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1500, 979);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		headerPanel = new HeaderPanel();
		contentPane.add(headerPanel);
		
		addPropertyNavigationLabels();
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(252, 165, 975, 767);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		propertyInfoPanel = new PropertyInfoPanel(propertyWrapper);
		layeredPane.add(propertyInfoPanel, "name_600211209077500");
		
		roomPricesPanel = new RoomsPricesPanel(rooms);
		layeredPane.add(roomPricesPanel, "name_604263933603500");
		
	}

	private void addPropertyNavigationLabels() {
		lblInfo = new JLabel("PROPERTY INFO");
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				switchPanel(propertyInfoPanel);
				updateUI(lblInfo);
			}
		});
		lblInfo.setBorder(new MatteBorder(0, 0, 0, 2, (Color) ColorConstants.LIGHT_GRAY));
		lblInfo.setForeground(ColorConstants.BLUE);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 18));
		lblInfo.setBounds(352, 101, 253, 43);
		contentPane.add(lblInfo);
		
		lblRoomPrices = new JLabel("ROOM & PRICES");
		lblRoomPrices.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchPanel(roomPricesPanel);
				updateUI(lblRoomPrices);
			}
		});
		lblRoomPrices.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomPrices.setForeground(ColorConstants.GRAY);
		lblRoomPrices.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRoomPrices.setBorder(new MatteBorder(0, 0, 0, 2, (Color) ColorConstants.LIGHT_GRAY));
		lblRoomPrices.setBounds(604, 101, 253, 43);
		contentPane.add(lblRoomPrices);
		
		lblGuestReviews = new JLabel("GUEST REVIEWS");
		lblGuestReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuestReviews.setForeground(ColorConstants.GRAY);
		lblGuestReviews.setFont(new Font("Dialog", Font.BOLD, 18));
		lblGuestReviews.setBounds(856, 101, 253, 43);
		contentPane.add(lblGuestReviews);
	}
	
	private void updateUI(JLabel label) {
		JLabel[] navLabels = {lblInfo, lblRoomPrices, lblGuestReviews};
		for (int i = 0; i < navLabels.length; i++) {
			if (label == navLabels[i]) {
				label.setForeground(ColorConstants.BLUE);
			} else {
				navLabels[i].setForeground(ColorConstants.GRAY);
			}
		}
		
	}

	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	
	
	

	
	
	
}
