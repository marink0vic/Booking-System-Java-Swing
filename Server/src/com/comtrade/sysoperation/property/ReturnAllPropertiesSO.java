package com.comtrade.sysoperation.property;

import java.sql.SQLException;

import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericMap;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnAllPropertiesSO extends GeneralSystemOperation<GenericMap<User, PropertyWrapper>> {

	@Override
	protected void executeSpecificOperation(GenericMap<User, PropertyWrapper> mapWrapper) throws SQLException {
		mapWrapper.setMap(ServerData.getInstance().returnAllProperties());
	}

}
