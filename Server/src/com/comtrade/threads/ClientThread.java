package com.comtrade.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.comtrade.constants.DomainType;
import com.comtrade.controller.IControllerBL;
import com.comtrade.controller.country.ControllerBLCountry;
import com.comtrade.controller.payment.ControllerBLPaymentType;
import com.comtrade.controller.property.ControllerBLProperty;
import com.comtrade.controller.user.ControllerBLUser;
import com.comtrade.transfer.TransferClass;

public class ClientThread extends Thread {
	
	private Socket socket;

	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				TransferClass transferClass = (TransferClass) inputStream.readObject();
				processRequest(transferClass);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void processRequest(TransferClass sender) {
		DomainType domainType = sender.getDomainType();
		TransferClass receiver = new TransferClass();
		
		switch (domainType) {
		case COUNTRY:
		{
			IControllerBL controller = new ControllerBLCountry();
			receiver = controller.executeOperation(sender);
			sendResponse(receiver);
			break;
		}
		case PAYMENT_TYPE:
		{
			IControllerBL controller = new ControllerBLPaymentType();
			receiver = controller.executeOperation(sender);
			sendResponse(receiver);
			break;
		}
		case USER:
		{
			IControllerBL controller = new ControllerBLUser();
			receiver = controller.executeOperation(sender);
			sendResponse(receiver);
			break;
		}
		case PROPERTY:
		{
			IControllerBL controller = new ControllerBLProperty();
			receiver = controller.executeOperation(sender);
			sendResponse(receiver);
			break;
		} 
		default:
			break;
		}
		
	}
	
	private void sendResponse(TransferClass transfer) {
		try{
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(transfer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	

}
