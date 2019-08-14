package com.comtrade.view.user.admin;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.comtrade.constants.ColorConstants;

public class HomePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;


	public HomePanel() {
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(435, 0, 1047, 803);
		this.setBackground(new Color(240, 240, 240));
		this.setLayout(null);
		setHomePanelLabels();
	}
	
	private void setHomePanelLabels() {
		int[] x_axis = {232,585};
		int[] y_axis = {175, 484};
		int[] txt_Y = {125, 434};
		
		String txt1 = "Number of users";
		String value1 = "36";
		setLabelOnScreen(x_axis[0], y_axis[0], txt_Y[0], txt1, value1);
	
		String txt2 = "Number of properties";
		String value2 = "9";
		setLabelOnScreen(x_axis[1], y_axis[0], txt_Y[0], txt2, value2);
	
		String txt3 = "Number of bookings";
		String value3 = "41";
		setLabelOnScreen(x_axis[0], y_axis[1], txt_Y[1], txt3, value3);

		String txt4 = "Total site earnings";
		String value4 = "256336";
		setLabelOnScreen(x_axis[1], y_axis[1], txt_Y[1], txt4, value4);
	}


	private void setLabelOnScreen(int x_axis, int y_axis, int txt_Y, String txt, String num_val) {
		JLabel lblNumOFUsers = new JLabel(txt);
		lblNumOFUsers.setForeground(ColorConstants.GRAY);
		lblNumOFUsers.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNumOFUsers.setBounds(x_axis, txt_Y, 233, 25);
		add(lblNumOFUsers);
		
		JLabel lbl_1 = new JLabel("");
		lbl_1.setOpaque(true);
		lbl_1.setBackground(new Color(119, 136, 153));
		lbl_1.setForeground(new Color(106, 90, 205));
		lbl_1.setBounds(x_axis, y_axis, 233, 16);
		add(lbl_1);
		
		JLabel lblNum = new JLabel(num_val);
		lblNum.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum.setForeground(ColorConstants.GRAY);
		lblNum.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNum.setOpaque(true);
		lblNum.setBackground(new Color(255, 255, 255));
		lblNum.setBounds(x_axis, y_axis, 233, 184);
		add(lblNum);
	}

}
