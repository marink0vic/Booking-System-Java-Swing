package com.comtrade.view.user.regular;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.view.user.regular.property.SelectedPropertyFrame;

public class SearchPagePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private List<PropertyWrapper> listOfProperties;
	private User user;
	private JLabel lblImage;
	private JLabel lblPropertyName;
	private JLabel lblStreet;
	private JButton btnReserve;
	private JLabel lblRating;
	private LocalDate checkIn;
	private LocalDate checkOut;
	
	public SearchPagePanel(List<PropertyWrapper> listOfProperties, User user) {
		this.listOfProperties = listOfProperties;
		this.user = user;
		initializeComponents();
	}
	
	private void initializeComponents() {
		this.setBounds(252, 204, 975, 728);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		listAllProperties();
	}
	
	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	private void listAllProperties() {
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(-1, 1));
		gridPanel.setBackground(new Color(255, 255, 255));
		gridPanel.setBounds(261, 562, 950, 320);
	    gridPanel.setAutoscrolls(true);
	    
	    
	    JScrollPane scrollPane = new JScrollPane(gridPanel);
	    scrollPane.setBorder(null);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBounds(261, 562, 950, 320);
	    
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(new Color(255, 255, 255));
		container.setBorder(null);
		container.setBounds(12, 0, 950, 715);
		container.add(scrollPane, BorderLayout.CENTER);
		this.add(container);
		
		for (PropertyWrapper property : listOfProperties) {
            JPanel scrollPanel = setScrollProperty(property);
            gridPanel.add(scrollPanel);
        }
		
	}

	private JPanel setScrollProperty(PropertyWrapper property) {
		JPanel testPanel = new JPanel();
		testPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(171, 171, 171)));
		testPanel.setOpaque(false);
		testPanel.setPreferredSize(new Dimension(950, 320));
		this.add(testPanel);
		testPanel.setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.setBackground(new Color(135, 206, 235));
		lblImage.setOpaque(true);
		lblImage.setBounds(12, 38, 282, 250);
		String icon = property.getImages().get(0).getImage();
		lblImage.setIcon(resizeImage(icon, lblImage.getWidth(), lblImage.getHeight()));
		testPanel.add(lblImage);
		
		lblPropertyName = new JLabel("");
		lblPropertyName.setBorder(null);
		lblPropertyName.setForeground(new Color(71, 71, 71));
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 22));
		lblPropertyName.setBackground(new Color(255, 255, 255));
		lblPropertyName.setBounds(330, 35, 351, 46);
		lblPropertyName.setText(property.getProperty().getName());
		testPanel.add(lblPropertyName);
		
		lblStreet = new JLabel("");
		lblStreet.setForeground(new Color(71, 71, 71));
		lblStreet.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblStreet.setBorder(null);
		lblStreet.setBackground(Color.WHITE);
		lblStreet.setBounds(330, 69, 351, 46);
		lblStreet.setText(property.getAddress().getStreet() + ", " + property.getAddress().getCity());
		testPanel.add(lblStreet);
		
		JTextArea txtAreaDescription = new JTextArea();
		txtAreaDescription.setForeground(new Color(71, 71, 71));
		txtAreaDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		txtAreaDescription.setBounds(330, 128, 489, 92);
		txtAreaDescription.setText(property.getProperty().getDescription());
		txtAreaDescription.setLineWrap(true);
		txtAreaDescription.setWrapStyleWord(true);
		testPanel.add(txtAreaDescription);
		
		btnReserve = new JButton("Book");
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SelectedPropertyFrame reservationFrame = new SelectedPropertyFrame(property, user, checkIn, checkOut);
				reservationFrame.setVisible(true);
				reservationFrame.setLocationRelativeTo(null);
			}
		});
		btnReserve.setForeground(new Color(255, 255, 255));
		btnReserve.setBackground(new Color(9, 121, 186));
		btnReserve.setFont(new Font("Dialog", Font.BOLD, 15));
		btnReserve.setBounds(330, 231, 195, 53);
		testPanel.add(btnReserve);
		
		lblRating = new JLabel("10");
		lblRating.setOpaque(true);
		lblRating.setForeground(new Color(255, 255, 255));
		lblRating.setBackground(new Color(9, 121, 186));
		lblRating.setHorizontalAlignment(SwingConstants.CENTER);
		lblRating.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRating.setBounds(866, 38, 56, 53);
		testPanel.add(lblRating);
		
		return testPanel;
	}
	
	private Icon resizeImage(String imgPath, int width, int height) {
		File file = new File(imgPath);
		Image image = new ImageIcon(file.getPath()).getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}

}
