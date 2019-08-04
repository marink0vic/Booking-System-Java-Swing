package com.comtrade.view.user.host;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.User;

public class ReviewPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel gridPanel;
	private JLabel lblCountryFlag;
	private JLabel lblCountryName;
	private JLabel lblProfilePicture;
	private JLabel lblRating;
	private List<PropertyReview> reviews;
	
	public ReviewPanel(List<PropertyReview> reviews) {
		this.reviews = reviews;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(-1, 1));
		gridPanel.setBackground(new Color(255, 255, 255));
		gridPanel.setBounds(180, 82, 760, 538);
	    gridPanel.setAutoscrolls(true);
	    
	    
	    JScrollPane scrollPane = new JScrollPane(gridPanel);
	    scrollPane.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBounds(180, 82, 760, 538);
	    
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(new Color(255, 255, 255));
		container.setBounds(180, 82, 760, 538);
		container.add(scrollPane, BorderLayout.CENTER);
		add(container);
		
		for (int i = 0; i < reviews.size(); i++) {
			PropertyReview propertyReview = reviews.get(i);
			JPanel panel = addRewiewPanelToScrollPane(propertyReview);
			gridPanel.add(panel);
		}
	}
	
	private JPanel addRewiewPanelToScrollPane(PropertyReview property_review) {
		JPanel review = new JPanel();
		User user = property_review.getUser();
		review.setBackground(new Color(255, 255, 255));
		review.setPreferredSize(new Dimension(760, 230));
		review.setBorder(new MatteBorder(0, 0, 1, 0, ColorConstants.LIGHT_GRAY));
		this.add(review);
		review.setLayout(null);
		
		lblProfilePicture = new JLabel("");
		lblProfilePicture.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfilePicture.setBounds(32, 23, 96, 85);
		lblProfilePicture.setIcon(setImage(user.getProfilePicture(), lblProfilePicture));
		review.add(lblProfilePicture);
		
		JLabel lblName = new JLabel();
		lblName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblName.setForeground(ColorConstants.GRAY);
		lblName.setBounds(140, 25, 406, 43);
		lblName.setText(user.getFirstName() + " " + user.getLastName());
		review.add(lblName);
		
		lblCountryFlag = new JLabel("");
		lblCountryFlag.setBounds(140, 71, 50, 37);
		lblCountryFlag.setIcon(setImage(user.getCountry().getImage(), lblCountryFlag));
		review.add(lblCountryFlag);
		
		lblCountryName = new JLabel();
		lblCountryName.setForeground(ColorConstants.GRAY);
		lblCountryName.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblCountryName.setBounds(202, 76, 229, 27);
		lblCountryName.setText(user.getCountry().getName());
		review.add(lblCountryName);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(ColorConstants.GRAY);
		textArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		textArea.setBounds(32, 133, 694, 96);
		textArea.setText(property_review.getComment());
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
		review.add(textArea);
		
		lblRating = new JLabel(property_review.getRating()+"");
		lblRating.setOpaque(true);
		lblRating.setForeground(new Color(255, 255, 255));
		lblRating.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRating.setHorizontalAlignment(SwingConstants.CENTER);
		lblRating.setBounds(670, 23, 56, 50);
		lblRating.setBackground(ColorConstants.BLUE);
		review.add(lblRating);
		
		return review;
	}
	
	private Icon setImage(String path, JLabel label) {
		File file = new File(path);
		ImageIcon imgIcon = new ImageIcon(file.getPath());
		Image img = imgIcon.getImage();
		Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

}
