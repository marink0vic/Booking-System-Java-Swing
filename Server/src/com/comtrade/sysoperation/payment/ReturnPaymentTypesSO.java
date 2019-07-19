package com.comtrade.sysoperation.payment;

import java.sql.SQLException;

import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PaymentType;
import com.comtrade.generics.GenericList;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnPaymentTypesSO extends GeneralSystemOperation<GenericList<PaymentType>> {

	@Override
	protected void executeSpecificOperation(GenericList<PaymentType> object) throws SQLException {
		object.setList(ServerData.getInstance().returnListOfPaymentTypes());
	}

}
