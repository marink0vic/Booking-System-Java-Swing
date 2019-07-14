package com.comtrade.controller;

import java.io.IOException;
import java.util.List;

import com.comtrade.communication.Communication;
import com.comtrade.constants.Operations;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.transfer.TransferClass;

public class ControllerUI {

private static ControllerUI controller;
	
	private ControllerUI() {
		
	}
	
	public static ControllerUI getController() {
		if (controller == null)
			controller = new ControllerUI();
		return controller;
	}
	
	public TransferClass returnCountriesList() throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setOperation(Operations.RETURN_ALL_COUNTRIES);
		return sendAndReturn(transferClass);
	}

	public TransferClass saveUser(User user) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setOperation(Operations.SAVE_USER);
		transferClass.setClientRequest(user);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass returnPaymentList() throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setOperation(Operations.RETURN_ALL_PAYMENT_TYPES);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass saveProperty(PropertyWrapper propertyWrapper) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(propertyWrapper);
		transferClass.setOperation(Operations.SAVE_ALL_PROPERTY_INFO);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass returnPropertyForOwner(PropertyWrapper propertyOwner) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(propertyOwner);
		transferClass.setOperation(Operations.RETURN_PROPERTY_FOR_OWNER);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass returnUser(User user) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(user);
		transferClass.setOperation(Operations.LOGIN_USER);
		return sendAndReturn(transferClass);
	}
	
	private TransferClass sendAndReturn(TransferClass transferClass) throws ClassNotFoundException, IOException {
		Communication.getCommunication().send(transferClass);
		TransferClass transferClass2 = Communication.getCommunication().read();
		return transferClass2;
	}

}
