package com.comtrade.sysoperation.property;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnUserPropertySO extends GeneralSystemOperation<PropertyWrapper> {

	@Override
	protected void executeSpecificOperation(PropertyWrapper owner) throws SQLException {
		IBroker iBroker = new Broker();
		owner = iBroker.returnPropertyForOwner(owner);
	}

}
