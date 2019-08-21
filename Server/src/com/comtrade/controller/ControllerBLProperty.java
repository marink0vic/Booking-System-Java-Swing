package com.comtrade.controller;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.generics.GenericList;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.property.ReturnAllPropertiesSO;
import com.comtrade.sysoperation.property.ReturnUserPropertySO;
import com.comtrade.sysoperation.property.SavePropertySO;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.ServerForm;

public class ControllerBLProperty implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case SAVE_ALL_PROPERTY_INFO:
		{
			PropertyWrapper propertyData = (PropertyWrapper) sender.getClientRequest();
			try {
				PropertyWrapper owner = saveProperty(propertyData);
				receiver.setServerResponse(owner);
				receiver.setDomainType(DomainType.PROPERTY);
				receiver.setOperation(Operations.SAVE_ALL_PROPERTY_INFO);
				appendNewPropertyToServerUI(owner);
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
				receiver.setDomainType(DomainType.PROPERTY);
				receiver.setOperation(Operations.RETURN_PROPERTY_FOR_OWNER);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while retrieving property information");
				e.printStackTrace();
			}
			return receiver;
		}
		case RETURN_ALL:
		{
			try {
				List<PropertyWrapper> property = returnAllProperties();
				receiver.setMessageResponse("All properties loaded from database");
				receiver.setServerResponse(property);
				receiver.setDomainType(DomainType.PROPERTY);
				receiver.setOperation(Operations.RETURN_ALL);
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

	private List<PropertyWrapper> returnAllProperties() throws SQLException {
		GenericList<PropertyWrapper> genericList = new GenericList<>();
		GeneralSystemOperation<GenericList<PropertyWrapper>> sysOperation = new ReturnAllPropertiesSO();
		sysOperation.executeSystemOperation(genericList);
		return genericList.getList();
	}

	private PropertyWrapper returnPropertyForOwner(PropertyWrapper propertyWrapper) throws SQLException {
		GenericClass<PropertyWrapper> genericClass = new GenericClass<>(propertyWrapper);
		GeneralSystemOperation<GenericClass<PropertyWrapper>> sysOperation = new ReturnUserPropertySO();
		sysOperation.executeSystemOperation(genericClass);
		return genericClass.getDomain();
	}

	private PropertyWrapper saveProperty(PropertyWrapper propertyData) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SavePropertySO();
		sysOperation.executeSystemOperation(propertyData);
		return propertyData;
	}
	
	private void appendNewPropertyToServerUI(PropertyWrapper owner) {
		String txt = "User " + owner.getUser().getUsername() + " has registered the property " + owner.getProperty().getName() + "\n";
		ServerForm.txtAreaServer.append(txt);
	}

}
