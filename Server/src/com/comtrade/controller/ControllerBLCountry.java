package com.comtrade.controller;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.constants.Operations;
import com.comtrade.domain.Country;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.generics.GenericList;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.country.ReturnCountriesSO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLCountry implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case RETURN_ALL:
		{
			List<Country> countries;
			try {
				countries = getAllCountries();
				receiver.setServerResponse(countries);
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

	private List<Country> getAllCountries() throws SQLException {
		GenericList<Country> genericList = new GenericList<>();
		GeneralSystemOperation<GenericList<Country>> sysOperation = new ReturnCountriesSO();
		sysOperation.executeSystemOperation(genericList);
		return genericList.getList();
	}

}
