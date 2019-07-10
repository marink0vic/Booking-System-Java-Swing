package com.comtrade.view.property;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.comtrade.constants.RoomTypeConstants;
import com.comtrade.domain.RoomType;

public class RoomTypePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	//-----roomType
	private JPanel addRoomType;
	private JPanel moreRoomType;
	private JTextField tfPrice;
	private JComboBox<RoomTypeConstants> comboRoomType;
	//---more rooms
	private JLabel lblYourCurrentInserted;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel();
	private JButton btnAddAnotherRoom;
	private JButton btnContinueNext;
	//----
	private JLayeredPane layeredPane;
	private List<RoomType> listOfTypes;
	private RoomPanel roomPanel;
	private JLabel lblRoomtype;
	private JLabel lblRoomsInfo;

	/**
	 * Create the panel.
	 */
	public RoomTypePanel(JLayeredPane layeredPane, List<RoomType> listOfTypes, RoomPanel roomPanel, JLabel lblRoomtype, JLabel lblRoomsInfo) {
		this.layeredPane = layeredPane;
		this.listOfTypes = listOfTypes;
		this.roomPanel = roomPanel;
		this.lblRoomtype = lblRoomtype;
		this.lblRoomsInfo = lblRoomsInfo;
		initializeComponents();
	}



	private void initializeComponents() {
		this.setBounds(0, 120, 1282, 783);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		JLayeredPane roomLayeredPane = new JLayeredPane();
		roomLayeredPane.setBounds(0, 0, 1282, 783);
		this.add(roomLayeredPane);
		roomLayeredPane.setLayout(new CardLayout(0, 0));
		
		addRoomType = new JPanel();
		addRoomType.setBackground(new Color(255, 255, 255));
		roomLayeredPane.add(addRoomType, "name_233721651129900");
		addRoomType.setLayout(null);
		
		JLabel lblTypeOfRoom = new JLabel("Type of room:");
		lblTypeOfRoom.setForeground(new Color(71, 71, 71));
		lblTypeOfRoom.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTypeOfRoom.setBounds(289, 152, 174, 37);
		addRoomType.add(lblTypeOfRoom);
		
		comboRoomType = new JComboBox<>();
		comboRoomType.setForeground(new Color(71, 71, 71));
		comboRoomType.setFont(new Font("Dialog", Font.BOLD, 17));
		comboRoomType.setBounds(612, 141, 389, 56);
		addRoomType.add(comboRoomType);
		
		JLabel lblNumberOfRoom = new JLabel("Number of room(of this type)");
		lblNumberOfRoom.setForeground(new Color(71, 71, 71));
		lblNumberOfRoom.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNumberOfRoom.setBounds(289, 238, 291, 37);
		addRoomType.add(lblNumberOfRoom);
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 5, 1);
		JSpinner spinner = new JSpinner(sm);
		spinner.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner.setBounds(612, 226, 62, 49);
		addRoomType.add(spinner);
		
		JLabel lblPricePerNight = new JLabel("Price per night:");
		lblPricePerNight.setForeground(new Color(71, 71, 71));
		lblPricePerNight.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPricePerNight.setBounds(289, 316, 155, 37);
		addRoomType.add(lblPricePerNight);
		
		tfPrice = new JTextField();
		tfPrice.setForeground(new Color(71, 71, 71));
		tfPrice.setFont(new Font("Dialog", Font.BOLD, 19));
		tfPrice.setColumns(10);
		tfPrice.setBounds(612, 307, 389, 55);
		addRoomType.add(tfPrice);
		
		JButton btnAddRoom = new JButton("Add room");
		btnAddRoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String typeOfRoom = String.valueOf(comboRoomType.getSelectedItem());
				if (!listContains(typeOfRoom)) {
					int numOfRooms = (Integer) spinner.getValue();
					Double pricePerNight = Double.valueOf(tfPrice.getText());
					RoomType roomType = new RoomType(typeOfRoom, numOfRooms, pricePerNight);
					
					listOfTypes.add(roomType);
					fillTable();
					switchPanel(moreRoomType);
				} else {
					JOptionPane.showMessageDialog(null, "You have already chosen that room type");
				}
			}
		});
		btnAddRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAddRoom.setBounds(356, 427, 531, 60);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnAddRoom.setBounds(358, 429, 527, 55);
			}
		});
		btnAddRoom.setForeground(Color.WHITE);
		btnAddRoom.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAddRoom.setBorder(null);
		btnAddRoom.setBackground(new Color(255, 88, 93));
		btnAddRoom.setBounds(358, 429, 527, 55);
		addRoomType.add(btnAddRoom);
		
		moreRoomType = new JPanel();
		moreRoomType.setBackground(new Color(255, 255, 255));
		roomLayeredPane.add(moreRoomType, "name_233754703723600");
		moreRoomType.setLayout(null);
		
		lblYourCurrentInserted = new JLabel("Your current inserted rooms:");
		lblYourCurrentInserted.setForeground(new Color(71, 71, 71));
		lblYourCurrentInserted.setFont(new Font("Dialog", Font.BOLD, 20));
		lblYourCurrentInserted.setBounds(126, 122, 302, 37);
		moreRoomType.add(lblYourCurrentInserted);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(130, 195, 881, 382);
		moreRoomType.add(scrollPane);
		
		table = new JTable(dtm);
		table.setForeground(new Color(71, 71, 71));
		table.setFont(new Font("Dialog", Font.BOLD, 17));
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		
		btnAddAnotherRoom = new JButton("Add another room");
		btnAddAnotherRoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddAnotherRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfPrice.setText("");
				comboRoomType.setSelectedIndex(0);
				spinner.setValue(1);
				switchPanel(addRoomType);
			}
		});
		btnAddAnotherRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAddAnotherRoom.setBounds(124, 627, 327, 60);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnAddAnotherRoom.setBounds(126, 629, 323, 55);
			}
		});
		btnAddAnotherRoom.setForeground(Color.WHITE);
		btnAddAnotherRoom.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAddAnotherRoom.setBorder(null);
		btnAddAnotherRoom.setBackground(new Color(9, 121, 186));
		btnAddAnotherRoom.setBounds(126, 629, 323, 55);
		moreRoomType.add(btnAddAnotherRoom);
		
		btnContinueNext = new JButton("Continue");
		btnContinueNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnContinueNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(roomPanel);
				updateUI(lblRoomtype,lblRoomsInfo);
				setHeaderValue();
			}
		});
		btnContinueNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnContinueNext.setBounds(490, 627, 327, 60);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnContinueNext.setBounds(492, 629, 323, 55);
			}
		});
		btnContinueNext.setForeground(Color.WHITE);
		btnContinueNext.setFont(new Font("Dialog", Font.BOLD, 20));
		btnContinueNext.setBorder(null);
		btnContinueNext.setBackground(new Color(255, 88, 93));
		btnContinueNext.setBounds(492, 629, 323, 55);
		moreRoomType.add(btnContinueNext);
		
		Object[] object = {"Room type", "Number of rooms", "Price per night"};
		dtm.addColumn(object[0]);
		dtm.addColumn(object[1]);
		dtm.addColumn(object[2]);
	
		fillCombo();
	}

	public void setHeaderValue() {
		   roomPanel.setHeaderValue(listOfTypes.get(0).getRoomType());
	}
	
	private boolean listContains(String typeOfRoom) {
		for (RoomType type : listOfTypes) {
			if (type.getRoomType().equals(typeOfRoom)) {
				return true;
			}
		}
		return false;
	}
	
	private void fillTable() {
		dtm.setRowCount(0);
		for (RoomType type : listOfTypes) {
			dtm.addRow(new Object[] {type.getRoomType(), type.getNumberOfRooms(), type.getPricePerNight()});
		}
		
	}
	
	private void fillCombo() {
		comboRoomType.addItem(RoomTypeConstants.STANDARD);
		comboRoomType.addItem(RoomTypeConstants.FAMILY);
		comboRoomType.addItem(RoomTypeConstants.STUDIO);
		comboRoomType.addItem(RoomTypeConstants.DELUXE);
		comboRoomType.addItem(RoomTypeConstants.APARTMENT);
	}
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void updateUI(JLabel label1, JLabel label2) {
		label1.setBorder(null);
		label2.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
	}

}
