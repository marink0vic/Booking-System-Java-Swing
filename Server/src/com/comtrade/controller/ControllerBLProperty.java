package com.comtrade.controller;

import java.sql.SQLException;

import com.comtrade.constants.Operations;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.property.ReturnUserPropertySO;
import com.comtrade.sysoperation.property.SavePropertySO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLProperty implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case SAVE_ALL_PROPERTY_INFO:
		{
			PropertyWrapper propertyWraper = (PropertyWrapper) sender.getClientRequest();
			try {
				User user = saveProperty(propertyWraper);
				receiver.setServerResponse(user);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while saving property to database");
				e.printStackTrace();
			}
			return receiver;
		}
		case RETURN_PROPERTY_FOR_OWNER:
		{
			PropertyWrapper propertyOwner = (PropertyWrapper) sender.getClientRequest();
			try {
				PropertyWrapper propertyWrapper = returnPropertyForOwner(propertyOwner);
				receiver.setServerResponse(propertyWrapper);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while retrieving property information");
				e.printStackTrace();
			}
			return receiver;
		}

		default:
			return null;
		}
	}

	private PropertyWrapper returnPropertyForOwner(PropertyWrapper propertyOwner) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new ReturnUserPropertySO();
		sysOperation.executeSystemOperation(propertyOwner);
		return propertyOwner;
	}

	private User saveProperty(PropertyWrapper propertyWraper) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SavePropertySO();
		sysOperation.executeSystemOperation(propertyWraper);
		return propertyWraper.getUser();
	}

}
