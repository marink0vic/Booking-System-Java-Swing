package com.comtrade.view.user.admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;

import com.comtrade.constants.ColorConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private JLabel lblHome;
	private JLabel lblProperties;
	private JLabel lblUsers;
	private JLabel tempLabel;
	private HomePanel homePanel;
	private JPanel PropertiesPanel;
	private UsersPanel usersPanel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public AdminFrame() {
		initializeComponents();
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 850);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(435, 0, 1047, 803);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		homePanel = new HomePanel();
		layeredPane.add(homePanel, "name_1132329764788000");
		
		PropertiesPanel = new JPanel();
		layeredPane.add(PropertiesPanel, "name_1133506876717400");
		PropertiesPanel.setLayout(null);
		
		usersPanel = new UsersPanel();
		layeredPane.add(usersPanel, "name_1133538685523500");

		setSideLabels();
		
	}

	private void setSideLabels() {
		JLabel lblProfilePic = new JLabel("New label");
		lblProfilePic.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblProfilePic.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblProfilePic.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfilePic.setBounds(124, 35, 175, 150);
		contentPane.add(lblProfilePic);
		
		JLabel lblName = new JLabel("Marko Marinkovic");
		lblName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblName.setForeground(ColorConstants.GRAY);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(72, 198, 280, 70);
		contentPane.add(lblName);
		
		lblHome = new JLabel("HOME");
		lblHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lblHome.setForeground(ColorConstants.BLUE);
				tempLabel.setForeground(ColorConstants.LIGHT_GRAY);
				tempLabel = lblHome;
				switchPanel(homePanel);
			}
		});
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblHome.setForeground(ColorConstants.BLUE);
		lblHome.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblHome.setBounds(124, 336, 170, 70);
		tempLabel = lblHome;
		contentPane.add(lblHome);
		
		lblProperties = new JLabel("PROPERTIES");
		lblProperties.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblProperties.setForeground(ColorConstants.BLUE);
				tempLabel.setForeground(ColorConstants.LIGHT_GRAY);
				tempLabel = lblProperties;
				switchPanel(PropertiesPanel);
			}
		});
		lblProperties.setHorizontalAlignment(SwingConstants.CENTER);
		lblProperties.setForeground(ColorConstants.LIGHT_GRAY);
		lblProperties.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblProperties.setBounds(124, 454, 170, 70);
		contentPane.add(lblProperties);
		
		lblUsers = new JLabel("USERS");
		lblUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblUsers.setForeground(ColorConstants.BLUE);
				tempLabel.setForeground(ColorConstants.LIGHT_GRAY);
				tempLabel = lblUsers;
				switchPanel(usersPanel);
			}
		});
		lblUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsers.setForeground(ColorConstants.LIGHT_GRAY);
		lblUsers.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblUsers.setBounds(124, 573, 170, 70);
		contentPane.add(lblUsers);
	}
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
}
