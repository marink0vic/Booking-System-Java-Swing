package com.comtrade.controller.payment;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.constants.Operations;
import com.comtrade.controller.IControllerBL;
import com.comtrade.domain.GeneralDomain;
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
		case RETURN_ALL_PAYMENT_TYPES:
		{
			List<GeneralDomain> paymentTypes;
			try {
				paymentTypes = getAllPaymentTypes();
				receiver.setServerResponse(paymentTypes);
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

	private List<GeneralDomain> getAllPaymentTypes() throws SQLException {
		GenericList<GeneralDomain> genericList = new GenericList<>();
		GeneralSystemOperation<GenericList<GeneralDomain>> sysOperation = new ReturnPaymentTypesSO();
		sysOperation.executeSystemOperation(genericList);
		return genericList.getList();
	}

}
