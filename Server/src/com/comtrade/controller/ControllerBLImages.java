package com.comtrade.controller;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.constants.Operations;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PropertyImage;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.generics.GenericList;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.country.ReturnCountriesSO;
import com.comtrade.sysoperation.images.SaveImagesSO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLImages implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case SAVE:
		{
			PropertyWrapper propertyOwner = (PropertyWrapper) sender.getClientRequest();
			try {
				saveImages(propertyOwner);
				receiver.setMessageResponse("The images were successfully saved in the database");
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while saving images to database");
				e.printStackTrace();
			}
			return receiver;
		}
		default:
		{
			return null;
		}
		}
	}

	private void saveImages(PropertyWrapper propertyOwner) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SaveImagesSO();
		sysOperation.executeSystemOperation(propertyOwner);
	}

}
