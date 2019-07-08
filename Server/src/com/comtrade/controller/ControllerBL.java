package com.comtrade.controller;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.domain.GeneralDomain;
import com.comtrade.generics.GenericList;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.country.ReturnCountriesSO;

public class ControllerBL {

	private static volatile ControllerBL instance;
	
	private ControllerBL() {
		
	}
	
	public static ControllerBL getController() {
		if (instance == null) {
			synchronized (ControllerBL.class) {
				if (instance == null) 
					instance = new ControllerBL();
			}
		}
		return instance;
	}

	public List<GeneralDomain> getAllCountries() throws SQLException {
		GenericList<GeneralDomain> genericList = new GenericList<>();
		GeneralSystemOperation<GenericList<GeneralDomain>> sysOperation = new ReturnCountriesSO();
		sysOperation.executeSystemOperation(genericList);
		return genericList.getList();
	}
}
