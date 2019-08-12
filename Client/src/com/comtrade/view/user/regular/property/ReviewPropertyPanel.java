package com.comtrade.view.user.regular.property;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
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
import com.comtrade.util.ImageResize;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReviewPropertyPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JLabel lblCountryFlag;
	private JLabel lblCountryName;
	private JLabel lblProfilePicture;
	private JLabel lblRating;
	private JPanel gridPanel;
	private List<PropertyReview> reviews;
	private UserReviewFrame addRevFrame = UserReviewFrame.getReviewFrame();

	public ReviewPropertyPanel(List<PropertyReview> reviews) {
		this.reviews = reviews;
		addRevFrame.setReviewPanel(this);
		initializeComponents();
	}
	

	private void initializeComponents() {
		this.setBounds(252, 165, 1150, 767);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		loadBasePanel();
		loadDinamicPanel();

		JButton btnAddReview = new JButton("ADD REVIEW");
		btnAddReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addRevFrame.setLocationRelativeTo(null);
				addRevFrame.setVisible(true);
			}
		});
		btnAddReview.setForeground(new Color(255, 255, 255));
		btnAddReview.setFont(new Font("Dialog", Font.BOLD, 16));
		btnAddReview.setBackground(ColorConstants.BLUE);
		btnAddReview.setBounds(940, 392, 158, 84);
		add(btnAddReview);
	}

	private void loadBasePanel() {
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(-1, 1));
		gridPanel.setBackground(new Color(255, 255, 255));
		gridPanel.setBounds(110, 120, 760, 600);
	    gridPanel.setAutoscrolls(true);
	    
	    
	    JScrollPane scrollPane = new JScrollPane(gridPanel);
	    scrollPane.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBounds(110, 120, 760, 600);
	    
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(new Color(255, 255, 255));
		container.setBounds(110, 120, 760, 600);
		container.add(scrollPane, BorderLayout.CENTER);
		this.add(container);
		
	}
	
	public void loadDinamicPanel() {
		gridPanel.removeAll();
		for (int i = 0; i < reviews.size(); i++) {
			JPanel panel = addRewiewPanelToScrollPane(reviews.get(i));
			gridPanel.add(panel);
		}
	}


	private JPanel addRewiewPanelToScrollPane(PropertyReview review) {	
		User user = review.getUser();
		JPanel temp = new JPanel();
		temp.setBackground(new Color(255, 255, 255));
		temp.setPreferredSize(new Dimension(760, 244));
		temp.setBorder(new MatteBorder(0, 0, 1, 0, ColorConstants.LIGHT_GRAY));
		this.add(temp);
		temp.setLayout(null);
		
		lblProfilePicture = new JLabel("");
		lblProfilePicture.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfilePicture.setBounds(32, 23, 96, 85);
		File f = new File(user.getProfilePicture());
		Icon i = ImageResize.resizeImage(f, lblProfilePicture.getWidth(), lblProfilePicture.getHeight());
		lblProfilePicture.setIcon(i);
		temp.add(lblProfilePicture);
		
		JLabel lblName = new JLabel();
		lblName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblName.setForeground(ColorConstants.GRAY);
		lblName.setBounds(140, 25, 406, 43);
		lblName.setText(user.getFirstName() + " " + user.getLastName());
		temp.add(lblName);
		
		lblCountryFlag = new JLabel("");
		lblCountryFlag.setBounds(140, 71, 50, 37);
		File f2 = new File(user.getCountry().getImage());
		Icon icon = ImageResize.resizeImage(f2, lblCountryFlag.getWidth(), lblCountryFlag.getHeight());
		lblCountryFlag.setIcon(icon);
		temp.add(lblCountryFlag);
		
		lblCountryName = new JLabel();
		lblCountryName.setForeground(ColorConstants.GRAY);
		lblCountryName.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblCountryName.setBounds(202, 76, 229, 27);
		lblCountryName.setText(user.getCountry().getName());
		temp.add(lblCountryName);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(ColorConstants.GRAY);
		textArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		textArea.setBounds(32, 133, 694, 96);
		textArea.setText(review.getComment());
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        temp.add(textArea);
		
		lblRating = new JLabel(review.getRating()+"");
		lblRating.setOpaque(true);
		lblRating.setForeground(new Color(255, 255, 255));
		lblRating.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRating.setHorizontalAlignment(SwingConstants.CENTER);
		lblRating.setBounds(670, 23, 56, 50);
		lblRating.setBackground(ColorConstants.BLUE);
		temp.add(lblRating);
		
		return temp;
	}


//	private Icon setImage(String path, JLabel label) {
//		File file = new File(path);
//		ImageIcon imgIcon = new ImageIcon(file.getPath());
//		Image img = imgIcon.getImage();
//		Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
//		ImageIcon image = new ImageIcon(newImg);
//		return image;
//	}
}
