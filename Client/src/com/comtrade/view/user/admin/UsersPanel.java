package com.comtrade.view.user.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.ColorConstants;

public class UsersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JComboBox<String> comboBox;
	private DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblNewLabel;
	private JLabel lblSiteEarnings;
	private List<String> tempList1 = Arrays.asList("Milica Pavlovic","Suipasa Hotel","18.10.58","55","255$");
	private List<String> tempList2 = Arrays.asList("Marko Marinkovic","Artezan","22.08.2019","158","1528$");

	public UsersPanel() {
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(435, 0, 1047, 803);
		this.setBackground(new Color(240, 240, 240));
		this.setLayout(null);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = (String)comboBox.getSelectedItem();
				fillTable(name);
			}
		});
		comboBox.setFont(new Font("Dialog", Font.BOLD, 16));
		comboBox.setForeground(ColorConstants.GRAY);
		comboBox.setBounds(63, 68, 299, 66);
		add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 195, 927, 345);
		add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(ColorConstants.GRAY);
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		Object[] object = {"Property name", "Booking created", "Booking number" , "Price"};
		dtm.addColumn(object[0]);
		dtm.addColumn(object[1]);
		dtm.addColumn(object[2]);
		dtm.addColumn(object[3]);
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("Price paid: 25833$");
		lblNewLabel.setForeground(ColorConstants.GRAY);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		lblNewLabel.setBounds(63, 578, 273, 46);
		add(lblNewLabel);
		
		lblSiteEarnings = new JLabel("Site earnings: 25833$");
		lblSiteEarnings.setForeground(new Color(71, 71, 71));
		lblSiteEarnings.setFont(new Font("Dialog", Font.BOLD, 22));
		lblSiteEarnings.setBounds(63, 624, 287, 46);
		add(lblSiteEarnings);
		
		fillComboBox();
		fillTable("Dusan Kecman");
	}
	
	private void fillTable(String name) {
		dtm.setRowCount(0);
		if (name.equals(tempList1.get(0))) {
			dtm.addRow(new Object[] {tempList1.get(1),tempList1.get(2),tempList1.get(3),tempList1.get(4)});
		} else if (name.equals(tempList2.get(0))) {
			dtm.addRow(new Object[] {tempList2.get(1),tempList2.get(2),tempList2.get(3),tempList2.get(4)});
		}
	}

	private void fillComboBox() {
		comboBox.addItem("Dusan Kecman");
		comboBox.addItem("Milica Pavlovic");
		comboBox.addItem("Ana Matic");
		comboBox.addItem("Marko Marinkovic");
		comboBox.addItem("Sonja Savic");
	}

}
