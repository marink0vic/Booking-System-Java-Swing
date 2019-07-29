package com.comtrade.controller;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.generics.GenericList;
import com.comtrade.generics.GenericMap;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.country.ReturnCountriesSO;
import com.comtrade.sysoperation.images.DeleteImagesSO;
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
			PropertyWrapper wrapper = (PropertyWrapper) sender.getClientRequest();
			try {
				
				wrapper  = saveImages(wrapper);
				receiver.setServerResponse(wrapper);
				receiver.setDomainType(DomainType.IMAGES);
				receiver.setOperation(Operations.SAVE);
				receiver.setMessageResponse("The images were successfully saved in the database");
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while saving images to database");
				e.printStackTrace();
			}
			return receiver;
		}
		case DELETE:
		{
			@SuppressWarnings("unchecked")
			List<PropertyImage> imagesForDeletion = (List<PropertyImage>) sender.getClientRequest();
			try {
				deleteImages(imagesForDeletion);
				receiver.setMessageResponse("The images were successfully deleted from the database");
				receiver.setDomainType(DomainType.IMAGES);
				receiver.setOperation(Operations.DELETE);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while deleting images from the database");
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

	private void deleteImages(List<PropertyImage> imagesForDeletion) throws SQLException {
		GenericList<PropertyImage> genericList = new GenericList<>();
		genericList.setList(imagesForDeletion);
		GeneralSystemOperation<GenericList<PropertyImage>> sysOperation = new DeleteImagesSO();
		sysOperation.executeSystemOperation(genericList);
	}

	private PropertyWrapper saveImages(PropertyWrapper wrapper) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SaveImagesSO();
		sysOperation.executeSystemOperation(wrapper);
		return wrapper;
	}

}
