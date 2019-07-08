package com.comtrade.controller;

import java.io.IOException;

import com.comtrade.communication.Communication;
import com.comtrade.constants.Operations;
import com.comtrade.domain.User;
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
	
	private TransferClass sendAndReturn(TransferClass transferClass) throws ClassNotFoundException, IOException {
		Communication.getCommunication().send(transferClass);
		TransferClass transferClass2 = Communication.getCommunication().read();
		return transferClass2;
	}

}
