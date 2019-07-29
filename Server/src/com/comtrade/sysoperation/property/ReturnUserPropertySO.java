package com.comtrade.sysoperation.property;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnUserPropertySO extends GeneralSystemOperation<GenericClass<PropertyWrapper>> {

	@Override
	protected void executeSpecificOperation(GenericClass<PropertyWrapper> generic) throws SQLException {
		PropertyWrapper pw = generic.getDomain();
		
		for (PropertyWrapper property : ServerData.getInstance().returnAllProperties()) {
			if (pw.getUser().getIdUser() == property.getUser().getIdUser()) {
				generic.setDomain(property);
				break;
			}
		}
	}

}
