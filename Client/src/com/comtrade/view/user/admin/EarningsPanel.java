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
import com.comtrade.domain.Transaction;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.time.Month;
import java.time.Year;

public class EarningsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<Transaction> transactions;
	private JLabel lblAmount;
	private JComboBox<Integer> comboYear;
	private JComboBox<Month> comboMonth;

	public EarningsPanel(List<Transaction> transactions) {
		this.transactions = transactions;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(435, 0, 1047, 803);
		this.setBackground(new Color(240, 240, 240));
		this.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setOpaque(true);
		separator.setBackground(new Color(171, 171, 171));
		separator.setBounds(260, 446, 526, 2);
		add(separator);
		
		JLabel lblMonthlyEarnings = new JLabel("Monthly earnings: ");
		lblMonthlyEarnings.setForeground(new Color(71, 71, 71));
		lblMonthlyEarnings.setFont(new Font("Dialog", Font.BOLD, 20));
		lblMonthlyEarnings.setBounds(260, 388, 184, 45);
		add(lblMonthlyEarnings);
		
		lblAmount = new JLabel("");
		lblAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAmount.setForeground(new Color(71, 71, 71));
		lblAmount.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAmount.setBounds(637, 388, 149, 45);
		add(lblAmount);
		
		comboYear = new JComboBox<>();
		comboYear.setForeground(new Color(71, 71, 71));
		comboYear.setFont(new Font("Dialog", Font.BOLD, 17));
		comboYear.setBounds(333, 284, 149, 45);
		add(comboYear);
		
		comboMonth = new JComboBox<>();
		comboMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateEarningsForMonth();
			}
		});
		comboMonth.setForeground(new Color(71, 71, 71));
		comboMonth.setFont(new Font("Dialog", Font.BOLD, 17));
		comboMonth.setBounds(517, 284, 184, 45);
		add(comboMonth);
		
		JLabel label_2 = new JLabel("Check your earnings for selected period");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(new Color(171, 171, 171));
		label_2.setFont(new Font("Dialog", Font.BOLD, 18));
		label_2.setBounds(333, 199, 368, 53);
		add(label_2);
		
		fillComboYear();
		fillComboMonth();
	}

	private void calculateEarningsForMonth() {
		int year = Integer.parseInt(String.valueOf(comboYear.getSelectedItem()));
		Month month = (Month) comboMonth.getSelectedItem();
		double amount = transactions
						.stream()
						.filter(y -> y.getTransferDate().getYear() == year)
						.filter(m -> m.getTransferDate().getMonth().compareTo(month) == 0)
						.mapToDouble(t -> t.getSiteFees())
						.sum();
		String num = String.format("%.2f", amount);
		lblAmount.setText(num);
	}

	private void fillComboYear() {
		int year = 2019;
		for (int i = year; i <= Year.now().getValue() ; i++) {
			comboYear.addItem(i);
		}
	}
	
	private void fillComboMonth() {
		Month[] months = Month.values();
		for (int i = 0; i < months.length; i++) {
			comboMonth.addItem(months[i]);
		}
	}
}
