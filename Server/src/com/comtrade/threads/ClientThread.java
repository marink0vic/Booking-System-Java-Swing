package com.comtrade.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerBL;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
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

	private void processRequest(TransferClass transferClass) {
		Operations operation = transferClass.getOperation();
		TransferClass transfer = new TransferClass();
		
		switch (operation) {
		case RETURN_ALL_COUNTRIES:
		{
			List<GeneralDomain> countries;
			try {
				countries = ControllerBL.getController().getAllCountries();
				transfer.setServerResponse(countries);
			} catch (SQLException e) {
				transfer.setMessageResponse("Problem occurred while returning data from database");
				e.printStackTrace();
			}
			sendResponse(transfer);
			break;
		}
		case RETURN_ALL_PAYMENT_TYPES:
		{
			List<GeneralDomain> paymentTypes;
			try {
				paymentTypes = ControllerBL.getController().getAllPaymentTypes();
				transfer.setServerResponse(paymentTypes);
			} catch (SQLException e) {
				transfer.setMessageResponse("Problem occurred while returning data from database");
				e.printStackTrace();
			}
			sendResponse(transfer);
			break;
		}
		case SAVE_USER:
		{
			GeneralDomain user = (GeneralDomain) transferClass.getClientRequest();
			try {
				user = ControllerBL.getController().saveUser(user);
				transfer.setServerResponse(user);
			} catch (SQLException e) {
				transfer.setMessageResponse("Problem occurred while saving user to database");
				e.printStackTrace();
			}
			sendResponse(transfer);
			break;
		}
		case LOGIN_USER:
		{
			User user = (User) transferClass.getClientRequest();
			try {
				user = ControllerBL.getController().loginUser(user);
				if (user == null) 
					transfer.setMessageResponse("Entered information does not exist in the database");
				transfer.setServerResponse(user);
			} catch (SQLException e) {
				transfer.setMessageResponse("Problem occurred while login user to database");
				e.printStackTrace();
			}
			sendResponse(transfer);
			break;
		}
		case SAVE_ALL_PROPERTY_INFO:
		{
			PropertyWrapper propertyWraper = (PropertyWrapper) transferClass.getClientRequest();
			try {
				User user = ControllerBL.getController().saveProperty(propertyWraper);
				transfer.setServerResponse(user);
			} catch (SQLException e) {
				transfer.setMessageResponse("Problem occurred while saving property to database");
				e.printStackTrace();
			}
			sendResponse(transfer);
			break;
		} 
		case RETURN_PROPERTY_FOR_OWNER:
		{
			PropertyWrapper propertyOwner = (PropertyWrapper) transferClass.getClientRequest();
			try {
				PropertyWrapper propertyWrapper = ControllerBL.getController().returnPropertyForOwner(propertyOwner);
				transfer.setServerResponse(propertyWrapper);
			} catch (SQLException e) {
				transfer.setMessageResponse("Problem occurred while retrieving property information");
				e.printStackTrace();
			}
			sendResponse(transfer);
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
