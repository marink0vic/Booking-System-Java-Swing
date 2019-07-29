package com.comtrade.controller;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PaymentType;
import com.comtrade.generics.GenericList;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.payment.ReturnPaymentTypesSO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLPaymentType implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case RETURN_ALL:
		{
			List<PaymentType> paymentTypes;
			try {
				paymentTypes = getAllPaymentTypes();
				receiver.setServerResponse(paymentTypes);
				receiver.setDomainType(DomainType.PAYMENT_TYPE);
				receiver.setOperation(Operations.RETURN_ALL);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while returning data from database");
				e.printStackTrace();
			}
			return receiver;
		}

		default:
			return null;
		}
	}

	private List<PaymentType> getAllPaymentTypes() throws SQLException {
		GenericList<PaymentType> genericList = new GenericList<>();
		GeneralSystemOperation<GenericList<PaymentType>> sysOperation = new ReturnPaymentTypesSO();
		sysOperation.executeSystemOperation(genericList);
		return genericList.getList();
	}

}
