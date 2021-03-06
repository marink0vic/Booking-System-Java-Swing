package com.comtrade.view.property;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.PropertyTypeConstants;
import com.comtrade.domain.Property;
import com.comtrade.domain.User;

public class BasicInfoPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	JComboBox<PropertyTypeConstants> comboType;
	private JTextField tfName;
	private JLabel lblPhoneNumber;
	private JTextField tfPhoneNumber;
	private JLabel lblAddDescriptionFor;
	private JLayeredPane layeredPane;
	private RoomTypePanel roomTypePanel;
	private JLabel lblRoomtype;
	private JSpinner ratingSpinner;
	private JTextArea textArea;
	
	private User user;
	private Property property;
	
	
	public BasicInfoPanel(JLayeredPane layeredPane, RoomTypePanel roomTypePanel, JLabel lblRoomtype) {
		this.layeredPane = layeredPane;
		this.roomTypePanel = roomTypePanel;
		this.lblRoomtype = lblRoomtype;
		initializeComponents();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	private void initializeComponents() {
		this.setBounds(0, 120, 1282, 783);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		comboType = new JComboBox<>();
		comboType.setFont(new Font("Dialog", Font.BOLD, 17));
		comboType.setBounds(316, 164, 389, 56);
		this.add(comboType);
		
		JLabel lblTypeOfProperty = new JLabel("Type of property:");
		lblTypeOfProperty.setForeground(new Color(71, 71, 71));
		lblTypeOfProperty.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTypeOfProperty.setBounds(77, 175, 174, 37);
		this.add(lblTypeOfProperty);
		
		tfName = new JTextField();
		tfName.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tfName.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
			}
		});
		tfName.setForeground(new Color(71, 71, 71));
		tfName.setFont(new Font("Dialog", Font.BOLD, 19));
		tfName.setColumns(10);
		tfName.setBounds(316, 255, 389, 55);
		this.add(tfName);
		
		JLabel lblPropertyName = new JLabel("Property name:");
		lblPropertyName.setForeground(new Color(71, 71, 71));
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPropertyName.setBounds(77, 266, 155, 37);
		this.add(lblPropertyName);
		
		lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setForeground(new Color(71, 71, 71));
		lblPhoneNumber.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPhoneNumber.setBounds(77, 357, 155, 37);
		this.add(lblPhoneNumber);
		
		tfPhoneNumber = new JTextField();
		tfPhoneNumber.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		tfPhoneNumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				tfPhoneNumber.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
			}
		});
		tfPhoneNumber.setForeground(ColorConstants.GRAY);
		tfPhoneNumber.setFont(new Font("Dialog", Font.BOLD, 19));
		tfPhoneNumber.setColumns(10);
		tfPhoneNumber.setBounds(316, 346, 389, 55);
		this.add(tfPhoneNumber);
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 5, 1);
		ratingSpinner = new JSpinner(sm);
		ratingSpinner.setFont(new Font("Dialog", Font.PLAIN, 17));
		ratingSpinner.setBounds(316, 433, 62, 49);
		this.add(ratingSpinner);
		
		JLabel lblRating = new JLabel("Rating:");
		lblRating.setForeground(new Color(71, 71, 71));
		lblRating.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRating.setBounds(77, 445, 155, 37);
		this.add(lblRating);
		
		lblAddDescriptionFor = new JLabel("Add description for your property:");
		lblAddDescriptionFor.setForeground(new Color(71, 71, 71));
		lblAddDescriptionFor.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAddDescriptionFor.setBounds(785, 113, 376, 37);
		this.add(lblAddDescriptionFor);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(785, 164, 394, 318);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textArea.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
			}
		});
		textArea.setForeground(new Color(71, 71, 71));
		textArea.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		textArea.setFont(new Font("Dialog", Font.PLAIN, 18));
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkFileds()) {
					createProperty();
					updateUI(lblRoomtype);
					switchPanel(roomTypePanel);	
				} else {
					JOptionPane.showMessageDialog(null, "You can't leave empty fields");
				}
			}
		});
		btnContinue.setForeground(Color.WHITE);
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 20));
		btnContinue.setBackground(new Color(255, 88, 93));
		btnContinue.setBounds(77, 570, 527, 55);
		this.add(btnContinue);
		
		fillComboPropertyType();
	}
	
	private boolean checkFileds() {
		int counter = 0;
		if (tfName.getText().length() == 0) {
			tfName.setBorder(new LineBorder(ColorConstants.RED));
			counter ++;
		}
		if (tfPhoneNumber.getText().length() == 0) {
			tfPhoneNumber.setBorder(new LineBorder(ColorConstants.RED));
			counter++;
		}
		if (textArea.getText().length() == 0) {
			textArea.setBorder(new LineBorder(ColorConstants.RED));
			counter++;
		}
		return counter == 0;
	}

	private void createProperty() {
		property.setIdUser(user.getIdUser());
		property.setType(comboType.getSelectedItem().toString());
		property.setName(tfName.getText());
		property.setPhoneNumner(tfPhoneNumber.getText());
		property.setRating((Integer)ratingSpinner.getValue());
		property.setDescription(textArea.getText());
	}

	private void fillComboPropertyType() {
		comboType.addItem(PropertyTypeConstants.HOTEL);
		comboType.addItem(PropertyTypeConstants.HOSTEL);
		comboType.addItem(PropertyTypeConstants.HOUSE);
		comboType.addItem(PropertyTypeConstants.APARTMENT);
	}
	
	protected void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	protected void updateUI(JLabel label) {
		label.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
	}


}
