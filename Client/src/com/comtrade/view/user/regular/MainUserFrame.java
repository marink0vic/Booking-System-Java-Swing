package com.comtrade.view.user.regular;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;

public class MainUserFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Map<User, PropertyWrapper> propertyMap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUserFrame frame = new MainUserFrame();
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
	public MainUserFrame() {
		propertyMap = new HashMap<>();
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		loadAllProperties();
		printProperties();
	}

	@SuppressWarnings("unchecked")
	private void loadAllProperties() {
		try {
			TransferClass transferClass = ControllerUI.getController().returnAllProperties();
			propertyMap = (Map<User, PropertyWrapper>) transferClass.getServerResponse();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void printProperties() {
		for (Map.Entry<User, PropertyWrapper> map : propertyMap.entrySet()) {
			User u = map.getKey();
			PropertyWrapper pw = map.getValue();
			System.out.println("User: " + u.getUsername() + ", Property name: " + pw.getProperty().getName());
		}
	}

}
