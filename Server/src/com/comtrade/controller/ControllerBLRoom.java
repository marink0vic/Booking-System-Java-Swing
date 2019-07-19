package com.comtrade.controller;

import java.sql.SQLException;

import com.comtrade.constants.Operations;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.room.SaveRoomSO;
import com.comtrade.sysoperation.room.UpdateRoomSO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLRoom implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case SAVE:
		{
			PropertyWrapper propertyOwner = (PropertyWrapper) sender.getClientRequest();
			try {
				propertyOwner = saveRoom(propertyOwner);
				receiver.setServerResponse(propertyOwner);
				receiver.setMessageResponse("New room type added to database");
			} catch (SQLException e) {
				receiver.setMessageResponse("Something went wrong");
				e.printStackTrace();
			}
			return receiver;
		}
		case UPDATE:
		{
			PropertyWrapper propertyOwner = (PropertyWrapper) sender.getClientRequest();
			try {
				updateRoom(propertyOwner);
				receiver.setMessageResponse("Room updated");
			} catch (SQLException e) {
				receiver.setMessageResponse("Something went wrong");
				e.printStackTrace();
			}
			return receiver;
		}

		default:
			return null;
		}
	}

	private void updateRoom(PropertyWrapper propertyOwner) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new UpdateRoomSO();
		sysOperation.executeSystemOperation(propertyOwner);
	}

	private PropertyWrapper saveRoom(PropertyWrapper propertyOwner) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SaveRoomSO();
		sysOperation.executeSystemOperation(propertyOwner);
		return propertyOwner;
	}

}
