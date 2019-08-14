package com.comtrade.view.user.admin;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.ColorConstants;

public class PropertiesPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblTotalSiteEarnings;
	
	public PropertiesPanel() {
		initializeComponents();
	}


	private void initializeComponents() {
		this.setBounds(435, 0, 1047, 803);
		this.setBackground(new Color(240, 240, 240));
		this.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(99, 139, 870, 447);
		add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		Object[] object = {"Property name", "Number of bookings", "Amount earned" , "Site fees"};
		dtm.addColumn(object[0]);
		dtm.addColumn(object[1]);
		dtm.addColumn(object[2]);
		dtm.addColumn(object[3]);
		scrollPane.setViewportView(table);
		
		lblTotalSiteEarnings = new JLabel("Total site fees: 25833$");
		lblTotalSiteEarnings.setForeground(new Color(71, 71, 71));
		lblTotalSiteEarnings.setFont(new Font("Dialog", Font.BOLD, 22));
		lblTotalSiteEarnings.setBounds(99, 616, 458, 46);
		add(lblTotalSiteEarnings);
	}

}
