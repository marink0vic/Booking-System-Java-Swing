package com.comtrade.view.user.host;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import com.comtrade.constants.ColorConstants;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.User;
import com.comtrade.dto.Message;
import com.comtrade.transfer.TransferClass;
import javax.swing.JScrollPane;

public class MessagePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTextArea textAreaReceive;
	private JTextArea textAreaSend;
	//private String messageToSend;
	private User userSender;
	private User propertyOwner;

	public MessagePanel(User propertyOwner) {
		this.propertyOwner = propertyOwner;
		initializeComponents();
	}

	private void initializeComponents() {
		this.setBounds(350, 136, 1132, 667);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(105, 92, 560, 528);
		add(scrollPane);
		
		textAreaReceive = new JTextArea();
		textAreaReceive.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		textAreaReceive.setForeground(ColorConstants.GRAY);
		textAreaReceive.setFont(new Font("Dialog", Font.BOLD, 19));
		textAreaReceive.setLineWrap(true);
		textAreaReceive.setWrapStyleWord(true);
		textAreaReceive.setEditable(false);
		scrollPane.setViewportView(textAreaReceive);
		
		textAreaSend = new JTextArea();
		textAreaSend.setBorder(new LineBorder(ColorConstants.LIGHT_GRAY));
		textAreaSend.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					String messageToSend = textAreaSend.getText();
					if (messageToSend.length() > 1) {
						sendMessageToClient(messageToSend);
					}
				}
			}
		});
		textAreaSend.setForeground(ColorConstants.GRAY);
		textAreaSend.setFont(new Font("Dialog", Font.BOLD, 19));
		textAreaSend.setBounds(707, 92, 358, 189);
		textAreaSend.setLineWrap(true);
		textAreaSend.setWrapStyleWord(true);
		add(textAreaSend);
		
		JButton btnNewButton = new JButton("SEND");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String messageToSend = textAreaSend.getText();
				if (messageToSend.length() > 1) {
					sendMessageToClient(messageToSend);
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 20));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(ColorConstants.BLUE);
		btnNewButton.setBounds(707, 325, 360, 79);
		add(btnNewButton);
	}
	
	public void showMessageToTextArea(Message message) {
		userSender = message.getSender();
		String name = userSender.getFirstName() + " " + userSender.getLastName() + ": ";
		String ms = name + message.getMessage();
		textAreaReceive.append(ms + "\n");

	}

	private void sendMessageToClient(String msg) {
		textAreaReceive.append("Me: " + msg + "\n");
		textAreaSend.setText("");	
		Message message = new Message(propertyOwner, userSender, msg);
		TransferClass transfer = new TransferClass();
		transfer.setClientRequest(message);
		transfer.setDomainType(DomainType.USER);
		transfer.setOperation(Operations.MESSAGE);
		
		ControllerUI.getController().sendToServer(transfer);
	}
}
