package com.comtrade.thread;

import com.comtrade.communication.Communication;
import com.comtrade.constants.DomainType;
import com.comtrade.controller.ControllerUI;
import com.comtrade.transfer.TransferClass;

public class ServerProcessThread implements Runnable {

	@Override
	public void run() {
		while (true) {
			TransferClass transfer = new TransferClass();
			try {
				transfer = Communication.getCommunication().read();
				processInformation(transfer);
			} catch (Exception e) {
				System.out.println("Communication failed");
				break;
			}
		}

	}

	private void processInformation(TransferClass transfer) {
		DomainType domain = transfer.getDomainType();
		switch (domain) {
		case COUNTRY:
		{
			ControllerUI.getController().processCountryFromServer(transfer);
			break;
		}
		case USER:
		{
			ControllerUI.getController().processUserFromServer(transfer);
			break;
		}
		case PAYMENT_TYPE:
		{
			ControllerUI.getController().processPaymentTypeFromServer(transfer);
			break;
		}
			

		default:
			break;
		}
	}

}
