package com.comtrade.controller;

import java.sql.SQLException;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.domain.PropertyReview;
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
			PropertyReview review = (PropertyReview) sender.getClientRequest();
			try {
				saveReview(review);
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

	private void saveReview(PropertyReview review) throws SQLException {
		GenericClass<PropertyReview> genericClass = new GenericClass<PropertyReview>(review);
		GeneralSystemOperation<GenericClass<PropertyReview>> sysOperation = new SaveReviewOS();
		sysOperation.executeSystemOperation(genericClass);
	}

}
