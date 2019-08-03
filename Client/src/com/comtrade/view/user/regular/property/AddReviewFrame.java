package com.comtrade.view.user.regular.property;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.ColorConstants;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddReviewFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfBookingID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddReviewFrame frame = new AddReviewFrame();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddReviewFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 966, 362);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblBookingID = new JLabel("Enter your booking number:");
		lblBookingID.setForeground(ColorConstants.GRAY);
		lblBookingID.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblBookingID.setBounds(88, 39, 197, 33);
		contentPane.add(lblBookingID);
		
		JLabel lblRating = new JLabel("Rate your experience:");
		lblRating.setForeground(new Color(71, 71, 71));
		lblRating.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblRating.setBounds(88, 97, 173, 33);
		contentPane.add(lblRating);
		
		JLabel lblWriteReview = new JLabel("Write review:");
		lblWriteReview.setForeground(new Color(71, 71, 71));
		lblWriteReview.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblWriteReview.setBounds(88, 143, 140, 33);
		contentPane.add(lblWriteReview);
		
		tfBookingID = new JTextField();
		tfBookingID.setHorizontalAlignment(SwingConstants.CENTER);
		tfBookingID.setForeground(ColorConstants.GRAY);
		tfBookingID.setFont(new Font("Dialog", Font.BOLD, 15));
		tfBookingID.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfBookingID.setBounds(297, 36, 46, 42);
		contentPane.add(tfBookingID);
		tfBookingID.setColumns(10);
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 10, 1);
		JSpinner spinner = new JSpinner(sm);
		spinner.setBounds(297, 94, 46, 42);
		contentPane.add(spinner);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(ColorConstants.GRAY);
		textArea.setFont(new Font("Dialog", Font.PLAIN, 16));
		textArea.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		textArea.setBounds(88, 183, 509, 86);
		contentPane.add(textArea);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int bookingId = Integer.parseInt(tfBookingID.getText());
					
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "You need to enter a number in input field");
				}
			}
		});
		btnSubmit.setForeground(new Color(255, 255, 255));
		btnSubmit.setBackground(ColorConstants.RED);
		btnSubmit.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSubmit.setBounds(693, 184, 148, 85);
		contentPane.add(btnSubmit);
	}
}
