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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
				transfer.setMessageResponse("Problem happened while returning data from database");
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
