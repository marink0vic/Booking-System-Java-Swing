package com.comtrade.view.user.host;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.comtrade.constants.ColorConstants;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Property;

public class EarningsPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> comboYear;
	private JComboBox<Month> comboMonth;
	private int year;
	private Month month;
	private JLabel label_1;
	private JLabel label_3;
	private JLabel label_5;
	private JLabel lblNightsCount;
	private JLabel lblRoomCount;
	private final double SITE_FEES = 0.1;
	private final double PROPERTY_CUT = 0.9;
	private Map<Booking, List<BookedRoom>> oldBookings;
	private Property property;

	public EarningsPanel(Map<Booking, List<BookedRoom>> oldBookings, Property property) {
		this.oldBookings = oldBookings;
		this.property = property;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		comboYear = new JComboBox<>();
		comboYear.setForeground(new Color(71, 71, 71));
		comboYear.setFont(new Font("Dialog", Font.BOLD, 17));
		comboYear.setBounds(309, 171, 149, 45);
		add(comboYear);
		
		comboMonth = new JComboBox<>();
		comboMonth.setForeground(new Color(71, 71, 71));
		comboMonth.setFont(new Font("Dialog", Font.BOLD, 17));
		comboMonth.setBounds(493, 171, 184, 45);
		add(comboMonth);
		
		JLabel lblCheckEarnings = new JLabel("Check your earnings for selected period");
		lblCheckEarnings.setForeground(ColorConstants.LIGHT_GRAY);
		lblCheckEarnings.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCheckEarnings.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheckEarnings.setBounds(395, 87, 368, 53);
		add(lblCheckEarnings);
		
		lblRoomCount = new JLabel("Room reserved: ");
		lblRoomCount.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRoomCount.setBounds(309, 276, 184, 45);
		lblRoomCount.setForeground(ColorConstants.GRAY);
		add(lblRoomCount);
		
		lblNightsCount = new JLabel("");
		lblNightsCount.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNightsCount.setForeground(ColorConstants.GRAY);
		lblNightsCount.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNightsCount.setBounds(686, 276, 149, 45);
		add(lblNightsCount);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(ColorConstants.LIGHT_GRAY);
		separator.setOpaque(true);
		separator.setBounds(309, 334, 526, 2);
		add(separator);
		
		JLabel lblMoneyPaid = new JLabel("Money paid: ");
		lblMoneyPaid.setForeground(ColorConstants.GRAY);
		lblMoneyPaid.setFont(new Font("Dialog", Font.BOLD, 20));
		lblMoneyPaid.setBounds(309, 364, 149, 45);
		add(lblMoneyPaid);
		
		label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.TRAILING);
		label_1.setForeground(new Color(71, 71, 71));
		label_1.setFont(new Font("Dialog", Font.BOLD, 20));
		label_1.setBounds(686, 364, 149, 45);
		add(label_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOpaque(true);
		separator_1.setBackground(ColorConstants.LIGHT_GRAY);
		separator_1.setBounds(309, 422, 526, 2);
		add(separator_1);
		
		JLabel lblSiteFees = new JLabel("Site fees: ");
		lblSiteFees.setForeground(ColorConstants.GRAY);
		lblSiteFees.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSiteFees.setBounds(309, 447, 149, 45);
		add(lblSiteFees);
		
		label_3 = new JLabel("");
		label_3.setHorizontalAlignment(SwingConstants.TRAILING);
		label_3.setForeground(new Color(71, 71, 71));
		label_3.setFont(new Font("Dialog", Font.BOLD, 20));
		label_3.setBounds(686, 447, 149, 45);
		add(label_3);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOpaque(true);
		separator_2.setBackground(ColorConstants.LIGHT_GRAY);
		separator_2.setBounds(309, 511, 526, 2);
		add(separator_2);
		
		JLabel lblTotalEarnings = new JLabel("Total revenue: ");
		lblTotalEarnings.setForeground(ColorConstants.GRAY);
		lblTotalEarnings.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTotalEarnings.setBounds(309, 530, 149, 45);
		add(lblTotalEarnings);
		
		label_5 = new JLabel("");
		label_5.setHorizontalAlignment(SwingConstants.TRAILING);
		label_5.setForeground(new Color(71, 71, 71));
		label_5.setFont(new Font("Dialog", Font.BOLD, 20));
		label_5.setBounds(686, 530, 149, 45);
		add(label_5);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOpaque(true);
		separator_3.setBackground(ColorConstants.LIGHT_GRAY);
		separator_3.setBounds(309, 588, 526, 2);
		add(separator_3);
		
		JButton btnShow = new JButton("SHOW");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				year = Integer.parseInt(comboYear.getSelectedItem().toString());
				month = (Month) comboMonth.getSelectedItem();
				calculateDataForMonth();
			}
		});
		btnShow.setFont(new Font("Dialog", Font.BOLD, 17));
		btnShow.setForeground(new Color(255, 255, 255));
		btnShow.setBounds(699, 171, 136, 45);
		btnShow.setBackground(ColorConstants.BLUE);
		add(btnShow);
		
		fillComboMonth();
		fillComboYear();
	}

	protected void calculateDataForMonth() {
		int roomReserved = 0;
		double moneyPaid = 0;
		for (Map.Entry<Booking, List<BookedRoom>> entry : oldBookings.entrySet()) {
			Booking b = entry.getKey();
			int y = b.getCheckIn().getYear();
			Month m = b.getCheckIn().getMonth();
			if (year == y && m.compareTo(month) == 0) {
				moneyPaid += b.getPriceForStay();
				for (BookedRoom br : entry.getValue()) {
					roomReserved += br.getNumberOfRooms();
				}
			}
		}
		updateUI(roomReserved, moneyPaid);
	}

	private void updateUI(int room_reserved, double money_paid) {
		lblNightsCount.setText(String.valueOf(room_reserved));
		String moneyPaid = String.format("%.2f", money_paid);
		label_1.setText(moneyPaid + " $");
		double site_fees = money_paid * SITE_FEES;
		double total = money_paid * PROPERTY_CUT;
		String fees = String.format("%.2f", site_fees);
		String totalEarnings = String.format("%.2f", total);
		label_3.setText(fees + " $");
		label_5.setText(totalEarnings + " $");
	}

	private void fillComboYear() {
		int year = property.getCreated().getYear();
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
