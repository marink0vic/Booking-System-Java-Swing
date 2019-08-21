package com.comtrade.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.threads.ServerThread;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextArea txtAreaServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
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
	public ServerForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 904, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnStart = new JButton("Start server");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServerThread serverThread = new ServerThread();
				serverThread.start();
				btnStart.setEnabled(false);
			}
		});
		btnStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnStart.setBorder(null);
		btnStart.setBackground(new Color(255, 88, 93));
		btnStart.setForeground(new Color(255, 255, 255));
		btnStart.setFont(new Font("Dialog", Font.BOLD, 18));
		btnStart.setBounds(251, 35, 366, 64);
		contentPane.add(btnStart);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 172, 862, 329);
		contentPane.add(scrollPane);
		
		txtAreaServer = new JTextArea();
		txtAreaServer.setForeground(new Color(71, 71, 71));
		txtAreaServer.setFont(new Font("Dialog", Font.BOLD, 19));
		txtAreaServer.setLineWrap(true);
		txtAreaServer.setWrapStyleWord(true);
		txtAreaServer.setEditable(false);
		scrollPane.setViewportView(txtAreaServer);
	}
}
