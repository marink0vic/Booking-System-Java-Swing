package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.login.IProxy;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserHomeFrame extends JFrame implements IProxy {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HeaderPanel headerPanel;
	private JLayeredPane layeredPane;
	private HomePagePanel homePagePanel;
	private SearchPagePanel searchPagePanel;
	
	private Map<User, PropertyWrapper> propertyMap;
	private List<PropertyWrapper> listOfProperties;
	private User user;
	private JTextField tfSearch;
	private JDateChooser dateCheckIn;
	private JDateChooser dateCheckOut;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private JLabel lblBackToHome;
	
	public UserHomeFrame(User user) {
		this.user = user;
		initializeComponents();
		propertyMap = new HashMap<>();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 979);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(169, 169, 169)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(252, 204, 975, 728);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		headerPanel = new HeaderPanel();
		contentPane.add(headerPanel);
		
		loadAllProperties();
		sortPropertiesBasedOnRating();
		setSearchPanel();
		
		
		homePagePanel = new HomePagePanel(listOfProperties);
		layeredPane.add(homePagePanel, "name_536942772642600");
		
		searchPagePanel = new SearchPagePanel(listOfProperties, user);
		layeredPane.add(searchPagePanel, "name_538271889712300");
		
		
	}

	private void setSearchPanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(0, 69, 1482, 135);
		searchPanel.setBackground(new Color(250, 250, 250));
		searchPanel.setLayout(null);
		contentPane.add(searchPanel);
		
		tfSearch = new JTextField();
		tfSearch.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfSearch.setOpaque(false);
		tfSearch.setForeground(ColorConstants.GRAY);
		tfSearch.setFont(new Font("Dialog", Font.PLAIN, 18));
		tfSearch.setBounds(263, 50, 353, 56);
		searchPanel.add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				if (dateCheckIn.getDate() != null && dateCheckOut.getDate() != null) {
					String in = date.format(dateCheckIn.getDate());
					String out = date.format(dateCheckOut.getDate());
					checkIn = LocalDate.parse(in);
					checkOut = LocalDate.parse(out);
					if (checkIn.isEqual(checkOut)) {
						JOptionPane.showMessageDialog(null, "You cannot enter the same dates");
					} else {
						searchPagePanel.setCheckIn(checkIn);
						searchPagePanel.setCheckOut(checkOut);
						switchPanel(searchPagePanel);
					}
				} else {
					JOptionPane.showMessageDialog(null, "You cannot leave empty date fields");
				}
				
			}
		});
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 15));
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.setBackground(new Color(9, 121, 186));
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
		
		dateCheckOut = new JDateChooser();
		dateCheckOut.setForeground(ColorConstants.GRAY);
		dateCheckOut.setFont(new Font("Dialog", Font.BOLD, 16));
		dateCheckOut.setBounds(861, 50, 191, 56);
		searchPanel.add(dateCheckOut);
		
		dateCheckIn = new JDateChooser();
		dateCheckIn.getDateEditor().addPropertyChangeListener(
			new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				dateCheckOut.setCalendar(null);
				dateCheckOut.setMinSelectableDate(dateCheckIn.getDate());
			 }
		});
		dateCheckIn.setMinSelectableDate(new Date());
		dateCheckIn.setForeground(ColorConstants.GRAY);
		dateCheckIn.setFont(new Font("Dialog", Font.BOLD, 16));
		dateCheckIn.setBounds(643, 50, 191, 56);
		searchPanel.add(dateCheckIn);
		
	}

	protected void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
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

	@Override
	public void login(User user) {
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
