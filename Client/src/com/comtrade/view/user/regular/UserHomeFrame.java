package com.comtrade.view.user.regular;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.login.IProxy;

import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JTextField tfStartDate;
	private JTextField tfEndDate;
	//---
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserHomeFrame frame = new UserHomeFrame(null);
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
		
		headerPanel = new HeaderPanel();
		contentPane.add(headerPanel);
		
		loadAllProperties();
		sortPropertiesBasedOnRating();
		setSearchPanel();
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(252, 204, 975, 728);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		homePagePanel = new HomePagePanel(listOfProperties);
		layeredPane.add(homePagePanel, "name_536942772642600");
		
		
		searchPagePanel = new SearchPagePanel(listOfProperties);
		layeredPane.add(searchPagePanel, "name_538271889712300");
		
		
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
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchPanel(searchPagePanel);
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
