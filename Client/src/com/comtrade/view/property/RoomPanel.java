package com.comtrade.view.property;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.MatteBorder;

import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;

public class RoomPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private String headerText = "Enter data for the room ";
	private JLabel lblHeaderText;
	private JSpinner spinner;
	private JLabel lblNumberOfBads;
	private JCheckBox cbKitchen;
	private JCheckBox cbWifi;
	private JCheckBox cbTv;
	private JCheckBox cbAirCon;
	private JButton btnNextRoom;
	private JButton btnContinue;
	private int tempRoomTypePosition;
	
	//----
	private JLayeredPane layeredPane;
	private List<RoomType> listOfTypes;
	private Map<RoomType, List<Room>> rooms;
	private JLabel lblRoomsInfo;
	private JLabel lblPropertyImages;
	private ImagesPanel imagesPanel;
	
	/**
	 * Create the panel.
	 */
	
	public RoomPanel(JLayeredPane layeredPane, List<RoomType> listOfTypes, Map<RoomType, List<Room>> rooms, JLabel lblRoomsInfo, JLabel lblPropertyImages, ImagesPanel imagesPanel) {
		this.layeredPane = layeredPane;
		this.listOfTypes = listOfTypes;
		this.rooms = rooms;
		this.lblRoomsInfo = lblRoomsInfo;
		this.lblPropertyImages = lblPropertyImages;
		this.imagesPanel = imagesPanel;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(0, 120, 1282, 783);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		lblHeaderText = new JLabel(headerText);
		lblHeaderText.setForeground(new Color(71, 71, 71));
		lblHeaderText.setFont(new Font("Dialog", Font.PLAIN, 23));
		lblHeaderText.setBounds(319, 143, 491, 80);
		this.add(lblHeaderText);
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 10, 1);
		spinner = new JSpinner(sm);
		spinner.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner.setBounds(563, 258, 64, 45);
		this.add(spinner);
		
		lblNumberOfBads = new JLabel("Number of bads");
		lblNumberOfBads.setForeground(new Color(71, 71, 71));
		lblNumberOfBads.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNumberOfBads.setBounds(368, 261, 183, 37);
		this.add(lblNumberOfBads);
		
		cbKitchen = new JCheckBox(" Kitchen");
		cbKitchen.setForeground(new Color(71, 71, 71));
		cbKitchen.setFont(new Font("Dialog", Font.BOLD, 17));
		cbKitchen.setBackground(new Color(255, 255, 255));
		cbKitchen.setBounds(368, 353, 113, 25);
		this.add(cbKitchen);
		
		cbWifi = new JCheckBox(" Wifi");
		cbWifi.setForeground(new Color(71, 71, 71));
		cbWifi.setFont(new Font("Dialog", Font.BOLD, 17));
		cbWifi.setBackground(Color.WHITE);
		cbWifi.setBounds(521, 355, 113, 25);
		this.add(cbWifi);
		
		cbTv = new JCheckBox(" TV");
		cbTv.setForeground(new Color(71, 71, 71));
		cbTv.setFont(new Font("Dialog", Font.BOLD, 17));
		cbTv.setBackground(Color.WHITE);
		cbTv.setBounds(368, 414, 113, 25);
		this.add(cbTv);
		
		cbAirCon = new JCheckBox(" Air conditioning");
		cbAirCon.setForeground(new Color(71, 71, 71));
		cbAirCon.setFont(new Font("Dialog", Font.BOLD, 17));
		cbAirCon.setBackground(Color.WHITE);
		cbAirCon.setBounds(521, 414, 174, 25);
		this.add(cbAirCon);
		
		btnNextRoom = new JButton("Next room");
		btnNextRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tempRoomTypePosition == listOfTypes.size()) {
					JOptionPane.showMessageDialog(null, "<html>You dont have any more room types <br> proceed to the next form</html>");
				} else {
					int numOfBads = (Integer) spinner.getValue();
					boolean kitchen = cbKitchen.isSelected();
					boolean tv = cbTv.isSelected();
					boolean airConditioning = cbAirCon.isSelected();
					boolean wifi = cbWifi.isSelected();
					
					Room r = new Room(numOfBads, kitchen, tv, airConditioning, wifi);
					addToMap(r);
				}
				
				clearInputs();
			}
		});
		btnNextRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNextRoom.setBounds(366, 487, 327, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNextRoom.setBounds(368, 489, 323, 55);
			}
		});
		btnNextRoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNextRoom.setForeground(Color.WHITE);
		btnNextRoom.setFont(new Font("Dialog", Font.BOLD, 20));
		btnNextRoom.setBorder(null);
		btnNextRoom.setBackground(new Color(9, 121, 186));
		btnNextRoom.setBounds(368, 489, 323, 55);
		this.add(btnNextRoom);
		
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tempRoomTypePosition != listOfTypes.size()) {
					JOptionPane.showMessageDialog(null, "Niste uneli podatke za sve sobe");
				} else {
					switchPanel(imagesPanel);
					updateUI(lblRoomsInfo, lblPropertyImages);	
				}
			}
		});
		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnContinue.setBounds(333, 635, 408, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnContinue.setBounds(335, 637, 404, 55);
			}
		});
		btnContinue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnContinue.setForeground(Color.WHITE);
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 20));
		btnContinue.setBorder(null);
		btnContinue.setBackground(new Color(255, 88, 93));
		btnContinue.setBounds(335, 637, 404, 55);
		this.add(btnContinue);
	}
	
	public void setHeaderValue(String roomType) {
		lblHeaderText.setText(headerText + roomType);
		
	}

	private void addToMap(Room r) {
		RoomType roomType = listOfTypes.get(tempRoomTypePosition);
		List<Room>list = new ArrayList<>(roomType.getNumberOfRooms());
		
		for (int i = 0; i < roomType.getNumberOfRooms(); i++) {
			list.add(r);
		}
		rooms.put(roomType, list);
		tempRoomTypePosition++;
		if (tempRoomTypePosition != listOfTypes.size()) lblHeaderText.setText(headerText + listOfTypes.get(tempRoomTypePosition).getRoomType());
		else lblHeaderText.setText("You entered all your rooms");
	}
	
	private void clearInputs() {
		spinner.setValue(1);
		cbTv.setSelected(false);
		cbAirCon.setSelected(false);
		cbWifi.setSelected(false);
		cbKitchen.setSelected(false);
	}
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void updateUI(JLabel label1, JLabel label2) {
		label1.setBorder(null);
		label2.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));// TODO Auto-generated method stub
	}
}
