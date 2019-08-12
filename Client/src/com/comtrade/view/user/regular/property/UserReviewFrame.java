package com.comtrade.view.user.regular.property;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;

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
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;

public class UserReviewFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final UserReviewFrame frame = new UserReviewFrame();
	private JPanel contentPane;
	private JTextField tfBookingID;
	private Map<Booking, List<BookedRoom>> myBookings;
	private List<PropertyReview> reviews;
	private int propertyID;
	private User user;
	private PropertyWrapper wrapper;
	private ReviewPropertyPanel reviewPropertyPanel;
	
	private UserReviewFrame() {
		initializeComponents();
	}
	
	public static UserReviewFrame getReviewFrame() {
		return frame;
	}

	public void setMyBookings(Map<Booking, List<BookedRoom>> myBookings) {
		this.myBookings = myBookings;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPropertyInfo(PropertyWrapper wrapper) {
		this.wrapper = wrapper;
		this.propertyID = wrapper.getProperty().getIdProperty();
		this.reviews = wrapper.getReviews();
	}

	public void setReviewPanel(ReviewPropertyPanel reviewPropertyPanel) {
		this.reviewPropertyPanel = reviewPropertyPanel;
	}

	private void initializeComponents() {
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
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
		contentPane.add(textArea);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int bookingId = Integer.parseInt(tfBookingID.getText());
					try {
						checkBookingID(bookingId);
						if (!checkReviews(bookingId)) {
							int rating = (int) spinner.getValue();
							String description = textArea.getText();
							PropertyReview propertyReview = new PropertyReview(bookingId, propertyID, user, rating, description);
							
							PropertyWrapper tempWrapper = new PropertyWrapper();
							tempWrapper.setUser(wrapper.getUser());
							tempWrapper.addNewReview(propertyReview);
							
							TransferClass transferClass = new TransferClass();
							transferClass.setClientRequest(tempWrapper);
							transferClass.setDomainType(DomainType.REVIEW);
							transferClass.setOperation(Operations.SAVE);
							
							ControllerUI.getController().sendToServer(transferClass);
							
							reviews.add(propertyReview);
							spinner.setValue(1);
							tfBookingID.setText("");
							textArea.setText("");
							
							reviewPropertyPanel.loadDinamicPanel();
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "You have already rated this propererty for ID: " + bookingId);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					
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

	protected boolean checkReviews(int booking_id) {
		for (PropertyReview r : reviews) {
			if (r.getIdBooking() == booking_id) {
				return true;
			}
		}
		return false;
	}

	private void checkBookingID(int booking_id) throws Exception {
		for (Booking b : myBookings.keySet()) {
			if (b.getIdBooking() == booking_id && b.getProperty().getIdProperty() == propertyID) {
				if (b.getStatus().equals("PENDING")) {
					throw new Exception("Your reservation is in pending stage.\n You can leave a comment after confirmation");
				} else {
					return;
				}
			}
		}
		throw new Exception("You have to reserve a room in this property to leave the review");
	}
}
