package com.comtrade.controller;

import java.sql.SQLException;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.domain.PropertyReview;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.review.SaveReviewOS;
import com.comtrade.transfer.TransferClass;

public class ControllerBLReview implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		switch (operation) {
		case SAVE:
		{
			PropertyWrapper wrapper = (PropertyWrapper) sender.getClientRequest();
			try {
				saveReview(wrapper);
				receiver.setOperation(Operations.SAVE);
				receiver.setDomainType(DomainType.REVIEW);
				receiver.setMessageResponse("Successfull insert");
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

	private void saveReview(PropertyWrapper wrapper) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SaveReviewOS();
		sysOperation.executeSystemOperation(wrapper);
	}

}
