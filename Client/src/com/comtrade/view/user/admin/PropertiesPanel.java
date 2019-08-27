package com.comtrade.view.user.admin;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.Transaction;
import com.comtrade.dto.AdminWrapper;
import com.comtrade.dto.PropertyWrapper;

public class PropertiesPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel();
	private AdminWrapper adminWrapper;
	
	public PropertiesPanel(AdminWrapper adminWrapper) {
		this.adminWrapper = adminWrapper;
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
		
		fillTable();
	}


	private void fillTable() {
		for (PropertyWrapper pw : adminWrapper.getAllProperties()) {
			String name = pw.getProperty().getName();
			int bookingCount = pw.getBookings().size();
			double[] earnings = calculateRevenue(pw.getProperty().getIdProperty());
			dtm.addRow(new Object[] {name, bookingCount, String.format("%.2f", earnings[0]), String.format("%.2f", earnings[1])});
		}
		
	}


	private double[] calculateRevenue(int id_property) {
		double amount = 0d, fees = 0d;
		for (Transaction transaction : adminWrapper.getTransactions()) {
			if (transaction.getIdReceiver() == id_property) {
				amount += transaction.getAmount();
				fees += transaction.getSiteFees();
			}
		}
		return new double[]{amount, fees};
	}
	

}
