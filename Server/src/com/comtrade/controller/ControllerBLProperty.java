package com.comtrade.controller;

import java.sql.SQLException;
import java.util.Map;

import com.comtrade.constants.Operations;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericMap;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.property.ReturnAllPropertiesSO;
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
			@SuppressWarnings("unchecked")
			GenericMap<User, PropertyWrapper> propertyData = (GenericMap<User, PropertyWrapper>) sender.getClientRequest();
			try {
				PropertyWrapper owner = saveProperty(propertyData);
				receiver.setServerResponse(owner);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while saving property to database");
				e.printStackTrace();
			}
			return receiver;
		}
		case RETURN_PROPERTY_FOR_OWNER:
		{
			PropertyWrapper propertyWrapper = (PropertyWrapper) sender.getClientRequest();
			try {
				propertyWrapper = returnPropertyForOwner(propertyWrapper);
				receiver.setServerResponse(propertyWrapper);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while retrieving property information");
				e.printStackTrace();
			}
			return receiver;
		}
		case RETURN_ALL:
		{
			try {
				Map<User, PropertyWrapper> property = returnAllProperties();
				receiver.setMessageResponse("All properties loaded from database");
				receiver.setServerResponse(property);
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

	private Map<User, PropertyWrapper> returnAllProperties() throws SQLException {
		GenericMap<User, PropertyWrapper> genericMap = new GenericMap<>();
		GeneralSystemOperation<GenericMap<User, PropertyWrapper>> sysOperation = new ReturnAllPropertiesSO();
		sysOperation.executeSystemOperation(genericMap);
		return genericMap.getMap();
	}

	private PropertyWrapper returnPropertyForOwner(PropertyWrapper propertyWrapper) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new ReturnUserPropertySO();
		sysOperation.executeSystemOperation(propertyWrapper);
		return propertyWrapper;
	}

	private PropertyWrapper saveProperty(GenericMap<User, PropertyWrapper> propertyData) throws SQLException {
		GeneralSystemOperation<GenericMap<User, PropertyWrapper>> sysOperation = new SavePropertySO();
		sysOperation.executeSystemOperation(propertyData);
		return propertyData.getValue(propertyData.getKey());
	}

}
