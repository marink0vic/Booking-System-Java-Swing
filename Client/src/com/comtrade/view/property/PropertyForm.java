package com.comtrade.view.property;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.PropertyTypeConstants;
import com.comtrade.constants.RoomTypeConstants;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.Country;
import com.comtrade.domain.User;
import com.comtrade.transfer.TransferClass;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JLayeredPane;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PropertyForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;
	private JLayeredPane layeredPane;
	
	//--- navigation labels and panel
	private JPanel navigationPanel = new JPanel();
	private JLabel lblAddress;
	private JLabel lblPropertyInfo;
	private JLabel lblRoomtype;
	private JLabel lblRoomsInfo;
	private JLabel lblPropertyImages;
	private JLabel lblPayment;
	
	//---- layered panels
	private AddressPanel addressPanel;
	private BasicInfoPanel basicInfoPanel;
	
	//-----roomType
	private JPanel RoomTypePanel = new JPanel();
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropertyForm frame = new PropertyForm(null);
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
	public PropertyForm(User user) {
		this.user = user;
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		createNavigationPanel();
		
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 120, 1282, 783);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		//--- panels
		basicInfoPanel = new BasicInfoPanel(layeredPane, RoomTypePanel, lblPropertyInfo, lblRoomtype);
		addressPanel = new AddressPanel(layeredPane, basicInfoPanel,lblAddress,lblPropertyInfo);
	
		
		layeredPane.add(addressPanel, "name_96510051729800");
		layeredPane.add(basicInfoPanel, "name_100717019548000");
		
		
		
		
		
		
		RoomTypePanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(RoomTypePanel, "name_153876851081600");
		RoomTypePanel.setLayout(null);
		
		JLayeredPane roomLayeredPane = new JLayeredPane();
		roomLayeredPane.setBounds(0, 0, 1282, 783);
		RoomTypePanel.add(roomLayeredPane);
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
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int numOfRooms = (Integer) spinner.getValue();
				Double pricePerNight = Double.valueOf(tfPrice.getText());
				String typeOfRoom = String.valueOf(comboRoomType.getSelectedItem());
				
				switchPanel(moreRoomType);
			}
		});
		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnContinue.setBounds(356, 427, 531, 60);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnContinue.setBounds(358, 429, 527, 55);
			}
		});
		btnContinue.setForeground(Color.WHITE);
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 20));
		btnContinue.setBorder(null);
		btnContinue.setBackground(new Color(255, 88, 93));
		btnContinue.setBounds(358, 429, 527, 55);
		addRoomType.add(btnContinue);
		
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
		scrollPane.setViewportView(table);
		
		btnAddAnotherRoom = new JButton("Add another room");
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
		btnAddAnotherRoom.setBackground(new Color(255, 88, 93));
		btnAddAnotherRoom.setBounds(126, 629, 323, 55);
		moreRoomType.add(btnAddAnotherRoom);
		
		btnContinueNext = new JButton("Continue");
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
	
	private void fillCombo() {
		comboRoomType.addItem(RoomTypeConstants.STANDARD);
		comboRoomType.addItem(RoomTypeConstants.FAMILY);
		comboRoomType.addItem(RoomTypeConstants.STUDIO);
		comboRoomType.addItem(RoomTypeConstants.DELUXE);
		comboRoomType.addItem(RoomTypeConstants.APARTMENT);
	}
	
	protected void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	protected void updateUI(JLabel label1, JLabel label2) {
		label1.setBorder(null);
		label2.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));// TODO Auto-generated method stub
	}

	
	
	private void createNavigationPanel() {
		navigationPanel.setBackground(new Color(71, 71, 71));
		navigationPanel.setBounds(0, 0, 1300, 121);
		contentPane.add(navigationPanel);
		navigationPanel.setLayout(null);
		
		lblAddress = new JLabel("Adress");
		lblAddress.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAddress.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setForeground(new Color(255, 255, 255));
		lblAddress.setFont(new Font("Dialog", Font.BOLD, 18));
		lblAddress.setBounds(57, 36, 158, 59);
		navigationPanel.add(lblAddress);
		
		lblPropertyInfo = new JLabel("Property info");
		lblPropertyInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyInfo.setForeground(Color.WHITE);
		lblPropertyInfo.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPropertyInfo.setBorder(null);
		lblPropertyInfo.setBounds(241, 35, 172, 60);
		navigationPanel.add(lblPropertyInfo);
		
		lblRoomtype = new JLabel("RoomType");
		lblRoomtype.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomtype.setForeground(Color.WHITE);
		lblRoomtype.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRoomtype.setBorder(null);
		lblRoomtype.setBounds(447, 35, 166, 60);
		navigationPanel.add(lblRoomtype);
		
		lblRoomsInfo = new JLabel("Rooms info");
		lblRoomsInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomsInfo.setForeground(Color.WHITE);
		lblRoomsInfo.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRoomsInfo.setBorder(null);
		lblRoomsInfo.setBounds(643, 35, 158, 60);
		navigationPanel.add(lblRoomsInfo);
		
		lblPropertyImages = new JLabel("Property images");
		lblPropertyImages.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyImages.setForeground(Color.WHITE);
		lblPropertyImages.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPropertyImages.setBorder(null);
		lblPropertyImages.setBounds(837, 35, 203, 60);
		navigationPanel.add(lblPropertyImages);
		
		lblPayment = new JLabel("Payment");
		lblPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblPayment.setForeground(Color.WHITE);
		lblPayment.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPayment.setBorder(null);
		lblPayment.setBounds(1070, 36, 144, 59);
		navigationPanel.add(lblPayment);
	}
}
