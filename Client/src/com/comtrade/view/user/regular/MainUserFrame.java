package com.comtrade.view.user.regular;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainUserFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Map<User, PropertyWrapper> propertyMap;
	private List<PropertyWrapper> listOfProperties;
	private JTextField tfSearch;
	private JTextField tfStartDate;
	private JTextField tfEndDate;
	private JLabel lblLeft;
	private JLabel lblMiddle;
	private JLabel lblRight;
	private JLabel lblSmallLeft;
	private JLabel lblSmallMiddle;
	private JLabel lblSmallRight;
	private JLabel lblImage;
	private JLabel lblPropertyName;
	private JLabel lblStreet;
	private JButton btnReserve;
	private JLabel lblRating;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUserFrame frame = new MainUserFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUserFrame() {
		propertyMap = new HashMap<>();
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 979);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(169, 169, 169)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		loadAllProperties();
		sortPropertiesBasedOnRating();
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 1482, 69);
		header.setBackground(new Color(95, 139, 161));
		contentPane.add(header);
		header.setLayout(null);
		
		JLabel lblLogo = new JLabel("Booking.com");
		lblLogo.setFont(new Font("Dialog", Font.BOLD, 26));
		lblLogo.setForeground(new Color(255, 255, 255));
		lblLogo.setBounds(50, 13, 184, 43);
		header.add(lblLogo);
		
		setSearchPanel();
		setTopDestinationsLabels();
		listAllProperties();
	
	}
	
	private void setSearchPanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(0, 69, 1482, 135);
		searchPanel.setBackground(new Color(250, 250, 250));
		searchPanel.setLayout(null);
		contentPane.add(searchPanel);
		
		tfSearch = new JTextField();
		tfSearch.setBorder(new LineBorder(new Color(171, 171, 171)));
		tfSearch.setOpaque(false);
		tfSearch.setForeground(new Color(71, 71, 71));
		tfSearch.setFont(new Font("Dialog", Font.PLAIN, 18));
		tfSearch.setBounds(263, 50, 353, 56);
		searchPanel.add(tfSearch);
		tfSearch.setColumns(10);
		
		tfStartDate = new JTextField();
		tfStartDate.setOpaque(false);
		tfStartDate.setForeground(new Color(71, 71, 71));
		tfStartDate.setFont(new Font("Dialog", Font.PLAIN, 18));
		tfStartDate.setColumns(10);
		tfStartDate.setBorder(new LineBorder(new Color(171, 171, 171)));
		tfStartDate.setBounds(645, 50, 191, 56);
		searchPanel.add(tfStartDate);
		
		tfEndDate = new JTextField();
		tfEndDate.setOpaque(false);
		tfEndDate.setForeground(new Color(71, 71, 71));
		tfEndDate.setFont(new Font("Dialog", Font.PLAIN, 18));
		tfEndDate.setColumns(10);
		tfEndDate.setBorder(new LineBorder(new Color(171, 171, 171)));
		tfEndDate.setBounds(861, 50, 191, 56);
		searchPanel.add(tfEndDate);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 15));
		btnSearch.setForeground(new Color(71, 71, 71));
		btnSearch.setBounds(1075, 50, 138, 56);
		searchPanel.add(btnSearch);
		
		JLabel lblWhere = new JLabel("WHERE");
		lblWhere.setFont(new Font("Dialog", Font.BOLD, 15));
		lblWhere.setForeground(new Color(71, 71, 71));
		lblWhere.setBounds(263, 21, 56, 16);
		searchPanel.add(lblWhere);
		
		JLabel lblCheckin = new JLabel("CHECK-IN");
		lblCheckin.setForeground(new Color(71, 71, 71));
		lblCheckin.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckin.setBounds(645, 21, 88, 16);
		searchPanel.add(lblCheckin);
		
		JLabel lblCheckout = new JLabel("CHECKOUT");
		lblCheckout.setForeground(new Color(71, 71, 71));
		lblCheckout.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCheckout.setBounds(861, 22, 98, 16);
		searchPanel.add(lblCheckout);
	}
	
	private void setTopDestinationsLabels() {
		JLabel lblTopDestinations = new JLabel("Top rated destinations");
		lblTopDestinations.setForeground(new Color(71, 71, 71));
		lblTopDestinations.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTopDestinations.setBounds(262, 236, 430, 43);
		contentPane.add(lblTopDestinations);
		
		lblSmallLeft = new JLabel("");
		lblSmallLeft.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallLeft.setOpaque(true);
		lblSmallLeft.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallLeft.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallLeft.setBackground(new Color(0, 0, 0, 150));
		lblSmallLeft.setForeground(new Color(255, 255, 255));
		lblSmallLeft.setBounds(262, 423, 302, 89);
		contentPane.add(lblSmallLeft);
		
		lblSmallMiddle = new JLabel("");
		lblSmallMiddle.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallMiddle.setOpaque(true);
		lblSmallMiddle.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallMiddle.setForeground(Color.WHITE);
		lblSmallMiddle.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallMiddle.setBackground(new Color(0, 0, 0, 150));
		lblSmallMiddle.setBounds(586, 423, 302, 89);
		contentPane.add(lblSmallMiddle);
		
		lblSmallRight = new JLabel("");
		lblSmallRight.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSmallRight.setOpaque(true);
		lblSmallRight.setHorizontalAlignment(SwingConstants.LEFT);
		lblSmallRight.setForeground(Color.WHITE);
		lblSmallRight.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblSmallRight.setBackground(new Color(0, 0, 0, 150));
		lblSmallRight.setBounds(909, 423, 302, 89);
		contentPane.add(lblSmallRight);
		
		lblLeft = new JLabel("");
		lblLeft.setOpaque(true);
		lblLeft.setBorder(null);
		lblLeft.setBounds(262, 302, 302, 210);
		contentPane.add(lblLeft);
		
		lblMiddle = new JLabel("");
		lblMiddle.setOpaque(true);
		lblMiddle.setBorder(null);
		lblMiddle.setBounds(586, 302, 302, 210);
		contentPane.add(lblMiddle);
		
		lblRight = new JLabel("");
		lblRight.setOpaque(true);
		lblRight.setBorder(null);
		lblRight.setBounds(909, 302, 302, 210);
		contentPane.add(lblRight);
		
		setTopDestinationIcons();
		setSmallLabelsText();
	}
	
	private void listAllProperties() {
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(-1, 1));
		gridPanel.setBackground(new Color(255, 255, 255));
		gridPanel.setBounds(261, 562, 950, 320);
	    gridPanel.setAutoscrolls(true);
	    
	    
	    JScrollPane scrollPane = new JScrollPane(gridPanel);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBounds(261, 562, 950, 320);
	    
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(null);
		container.setBounds(261, 562, 950, 320);
		container.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(container);
		
		for (PropertyWrapper property : listOfProperties) {
            JPanel scrollPanel = setScrollProperty(property);
            gridPanel.add(scrollPanel);
        }
	  
	}

	private JPanel setScrollProperty(PropertyWrapper property) {
		JPanel testPanel = new JPanel();
		testPanel.setBorder(new LineBorder(new Color(112, 128, 144)));
		testPanel.setOpaque(false);
		//testPanel.setBounds(261, 562, 950, 320);
		testPanel.setPreferredSize(new Dimension(950, 320));
		contentPane.add(testPanel);
		testPanel.setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.setBackground(new Color(135, 206, 235));
		lblImage.setOpaque(true);
		lblImage.setBounds(12, 13, 282, 294);
		String icon = property.getImages().get(0).getImage();
		lblImage.setIcon(resizeImage(icon, lblImage.getWidth(), lblImage.getHeight()));
		testPanel.add(lblImage);
		
		lblPropertyName = new JLabel("");
		lblPropertyName.setBorder(null);
		lblPropertyName.setForeground(new Color(71, 71, 71));
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 22));
		lblPropertyName.setBackground(new Color(255, 255, 255));
		lblPropertyName.setBounds(330, 25, 351, 46);
		lblPropertyName.setText(property.getProperty().getName());
		testPanel.add(lblPropertyName);
		
		lblStreet = new JLabel("this is street text");
		lblStreet.setForeground(new Color(71, 71, 71));
		lblStreet.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblStreet.setBorder(null);
		lblStreet.setBackground(Color.WHITE);
		lblStreet.setBounds(330, 59, 351, 46);
		lblStreet.setText(property.getAddress().getStreet() + ", " + property.getAddress().getCity());
		testPanel.add(lblStreet);
		
		JTextArea txtAreaDescription = new JTextArea();
		txtAreaDescription.setForeground(new Color(71, 71, 71));
		txtAreaDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		txtAreaDescription.setBounds(330, 118, 489, 92);
		txtAreaDescription.setText(property.getProperty().getDescription());
		txtAreaDescription.setLineWrap(true);
		txtAreaDescription.setWrapStyleWord(true);
		testPanel.add(txtAreaDescription);
		
		btnReserve = new JButton("Book");
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(property.getProperty().getName());
			}
		});
		btnReserve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnReserve.setBounds(328, 239, 199, 58);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnReserve.setBounds(330, 241, 195, 53);
			}
		});
		btnReserve.setBorder(null);
		btnReserve.setForeground(new Color(255, 255, 255));
		btnReserve.setBackground(new Color(9, 121, 186));
		btnReserve.setFont(new Font("Dialog", Font.BOLD, 15));
		btnReserve.setBounds(330, 241, 195, 53);
		testPanel.add(btnReserve);
		
		lblRating = new JLabel("10");
		lblRating.setOpaque(true);
		lblRating.setForeground(new Color(255, 255, 255));
		lblRating.setBackground(new Color(9, 121, 186));
		lblRating.setHorizontalAlignment(SwingConstants.CENTER);
		lblRating.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRating.setBounds(866, 26, 56, 53);
		testPanel.add(lblRating);
		
		return testPanel;
	}

	private void setTopDestinationIcons() {
		JLabel[] labels = {lblLeft, lblMiddle, lblRight};
		String imgPath;
		for (int i = 0; i < labels.length; i++) {
			imgPath = listOfProperties.get(i).getImages().get(0).getImage();
			labels[i].setIcon(resizeImage(imgPath, lblLeft.getWidth(), lblLeft.getHeight()));
		}
	}
	private void setSmallLabelsText() {
		JLabel[] labels = {lblSmallLeft, lblSmallMiddle, lblSmallRight};
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

	private Icon resizeImage(String imgPath, int width, int height) {
		File file = new File(imgPath);
		Image image = new ImageIcon(file.getPath()).getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}

	private void sortPropertiesBasedOnRating() {
		listOfProperties.sort(new Comparator<PropertyWrapper>() {
			@Override
			public int compare(PropertyWrapper wrapper1, PropertyWrapper wrapper2) {
				return wrapper2.getProperty().getRating() - wrapper1.getProperty().getRating();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void loadAllProperties() {
		try {
			TransferClass transferClass = ControllerUI.getController().returnAllProperties();
			propertyMap = (Map<User, PropertyWrapper>) transferClass.getServerResponse();
			listOfProperties = new ArrayList<>(propertyMap.values());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
