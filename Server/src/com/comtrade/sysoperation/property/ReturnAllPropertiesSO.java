package com.comtrade.sysoperation.property;

import java.sql.SQLException;

import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericList;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnAllPropertiesSO extends GeneralSystemOperation<GenericList<PropertyWrapper>> {

	@Override
	protected void executeSpecificOperation(GenericList<PropertyWrapper> listWrapper) throws SQLException {
		listWrapper.setList(ServerData.getInstance().returnAllProperties());
	}

}
