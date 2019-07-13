package com.comtrade.view.user.host;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.comtrade.dto.PropertyWrapper;

public class HomePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel();
	private PropertyWrapper propertyOwner;
	
	public HomePanel(PropertyWrapper propertyOwner) {
		this.propertyOwner = propertyOwner;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		JLabel lblPropertyName = new JLabel("Propery Name");
		lblPropertyName.setForeground(new Color(71, 71, 71));
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPropertyName.setBorder(null);
		lblPropertyName.setBounds(125, 71, 193, 37);
		this.add(lblPropertyName);
		
		JLabel lblPropertyAddress = new JLabel("Propery Address");
		lblPropertyAddress.setForeground(new Color(71, 71, 71));
		lblPropertyAddress.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblPropertyAddress.setBorder(null);
		lblPropertyAddress.setBounds(125, 107, 250, 37);
		this.add(lblPropertyAddress);
		
		JLabel lblSmallImage = new JLabel("");
		lblSmallImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblSmallImage.setBounds(25, 65, 86, 91);
		this.add(lblSmallImage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 207, 670, 224);
		this.add(scrollPane);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		JLabel arrowLeft = new JLabel("");
		arrowLeft.setHorizontalAlignment(SwingConstants.LEFT);
		arrowLeft.setBorder(null);
		arrowLeft.setBounds(25, 513, 56, 44);
		Icon iconLeftArrow = new ImageIcon("./resources/icons/arrow-left.png");
		arrowLeft.setIcon(iconLeftArrow);
		this.add(arrowLeft);
		
		JLabel arrowRight = new JLabel("");
		arrowRight.setHorizontalAlignment(SwingConstants.RIGHT);
		arrowRight.setBorder(null);
		arrowRight.setBounds(639, 513, 56, 44);
		Icon iconRightArrow = new ImageIcon("./resources/icons/arrow-right.png");
		arrowRight.setIcon(iconRightArrow);
		this.add(arrowRight);
		
		JLabel lblImage1 = new JLabel("");
		lblImage1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImage1.setBounds(93, 467, 152, 152);
		this.add(lblImage1);
		
		JLabel lblImage2 = new JLabel("");
		lblImage2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImage2.setBounds(281, 467, 152, 152);
		this.add(lblImage2);
		
		JLabel lblImage3 = new JLabel("");
		lblImage3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImage3.setBounds(475, 467, 152, 152);
		this.add(lblImage3);
	}
	
	

}
