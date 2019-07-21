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
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.comtrade.controller.ControllerUI;
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		
		JLabel lblTopDestinations = new JLabel("Top rated destinations");
		lblTopDestinations.setForeground(new Color(71, 71, 71));
		lblTopDestinations.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTopDestinations.setBounds(262, 236, 430, 43);
		contentPane.add(lblTopDestinations);
		
		lblLeft = new JLabel("");
		lblLeft.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLeft.setBounds(262, 302, 302, 210);
		contentPane.add(lblLeft);
		
		lblMiddle = new JLabel("");
		lblMiddle.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblMiddle.setBounds(586, 302, 302, 210);
		contentPane.add(lblMiddle);
		
		lblRight = new JLabel("");
		lblRight.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblRight.setBounds(909, 302, 302, 210);
		contentPane.add(lblRight);
		
		JPanel testPanel = new JPanel();
		testPanel.setBounds(261, 562, 950, 320);
		contentPane.add(testPanel);
		testPanel.setLayout(null);
		
		
		
//		JPanel p2 = new JPanel();
//        p2.setLayout(new GridLayout(-1, 1));
//        p2.setBackground(new Color(255, 255, 255));
//        p2.setBounds(0, 0, 950, 280);
//        p2.setAutoscrolls(true);
//
//        JScrollPane scrollPane = new JScrollPane(p2);
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setBounds(0, 0, 955, 320);
//		
//		JPanel container = new JPanel(new BorderLayout());
//		container.setBounds(263, 571, 950, 320);
//		container.add(scrollPane, BorderLayout.CENTER);
//		contentPane.add(container);
//		
//		for (int i = 0; i < 10; i++) {
//
//            JPanel sp1 = new JPanel();
//            sp1.setLayout(null);
//            sp1.setBackground(Color.WHITE);
//            sp1.setPreferredSize(new Dimension(950, 280));
//            sp1.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(105, 105, 105)));
//
//            p2.add(sp1);
//
//        }
//        
		
		loadAllProperties();
		setTopDestinationPanels();
	}
	
//	private void addPropertiesToPanel() {
//		for (Map.Entry<User, PropertyWrapper> entry : propertyMap.entrySet()) {
//			PropertyWrapper tempWrapper = entry.getValue();
//			container.add(createPropertyPanel(tempWrapper));
//		}
//	}

	private JPanel createPropertyPanel(PropertyWrapper tempWrapper) {
		return null;
	}

	private void setTopDestinationPanels() {
		sortPropertiesBasedOnRating();
		JLabel[] labels = {lblLeft, lblMiddle, lblRight};
		String imgPath;
		for (int i = 0; i < 3; i++) {
			imgPath = listOfProperties.get(i).getImages().get(0).getImage();
			labels[i].setIcon(resizeImage(imgPath));
		}
	}
	
	private Icon resizeImage(String imgPath) {
		File file = new File(imgPath);
		Image image = new ImageIcon(file.getPath()).getImage();
		Image newImage = image.getScaledInstance(lblLeft.getWidth(), lblLeft.getHeight(), Image.SCALE_SMOOTH);
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
