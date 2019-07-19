package com.comtrade.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.comtrade.constants.DomainType;
import com.comtrade.controller.ControllerBLCountry;
import com.comtrade.controller.ControllerBLImages;
import com.comtrade.controller.ControllerBLPaymentType;
import com.comtrade.controller.ControllerBLProperty;
import com.comtrade.controller.ControllerBLRoom;
import com.comtrade.controller.ControllerBLUser;
import com.comtrade.controller.IControllerBL;
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
				//e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
				break;
			}
		}
	}

	private void processRequest(TransferClass sender) {
		DomainType domainType = sender.getDomainType();
		TransferClass receiver = new TransferClass();
		IControllerBL controller = null;
		switch (domainType) {
		case COUNTRY:
		{
			controller = new ControllerBLCountry();
			break;
		}
		case PAYMENT_TYPE:
		{
			controller = new ControllerBLPaymentType();
			break;
		}
		case USER:
		{
			controller = new ControllerBLUser();
			break;
		}
		case PROPERTY:
		{
			controller = new ControllerBLProperty();
			break;
		} 
		case IMAGES:
		{
			controller = new ControllerBLImages();
			break;
		}
		case ROOM:
		{
			controller = new ControllerBLRoom();
			break;
		}
		default:
			break;
		}
		receiver = controller.executeOperation(sender);
		sendResponse(receiver);
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
