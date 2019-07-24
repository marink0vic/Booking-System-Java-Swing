package com.comtrade.controller;

import java.sql.SQLException;

import com.comtrade.constants.Operations;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.booking.SaveBookingSO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLBooking implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		TransferClass receiver = new TransferClass();
		Operations operation = sender.getOperation();
		switch (operation) {
		case SAVE:
			PropertyWrapper propertyWrapper = (PropertyWrapper) sender.getClientRequest();
			PropertyWrapper savedBooking;
			try {
				savedBooking = saveBooking(propertyWrapper);
				receiver.setServerResponse(savedBooking);
				receiver.setMessageResponse("Booking saved");
			} catch (SQLException e) {
				receiver.setMessageResponse("Something went wrong");
				e.printStackTrace();
			}
			return receiver;

		default:
			return null;
		}
		
	}

	private PropertyWrapper saveBooking(PropertyWrapper propertyWrapper) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SaveBookingSO();
		sysOperation.executeSystemOperation(propertyWrapper);
		return propertyWrapper;
	}

}
