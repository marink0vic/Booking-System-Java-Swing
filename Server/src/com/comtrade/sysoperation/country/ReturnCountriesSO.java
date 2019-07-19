package com.comtrade.sysoperation.country;

import java.sql.SQLException;

import com.comtrade.domain.Country;
import com.comtrade.generics.GenericList;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnCountriesSO extends GeneralSystemOperation<GenericList<Country>> {

	@Override
	protected void executeSpecificOperation(GenericList<Country> object) throws SQLException {
		object.setList(ServerData.getInstance().returnListOfCountries());
	}

}
