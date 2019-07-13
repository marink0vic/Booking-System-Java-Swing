package com.comtrade.controller;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.generics.GenericList;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.country.ReturnCountriesSO;
import com.comtrade.sysoperation.payment.ReturnPaymentTypesSO;
import com.comtrade.sysoperation.property.ReturnUserPropertySO;
import com.comtrade.sysoperation.property.SavePropertySO;
import com.comtrade.sysoperation.user.SaveUserSO;

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
	
	public List<GeneralDomain> getAllPaymentTypes() throws SQLException {
		GenericList<GeneralDomain> genericList = new GenericList<>();
		GeneralSystemOperation<GenericList<GeneralDomain>> sysOperation = new ReturnPaymentTypesSO();
		sysOperation.executeSystemOperation(genericList);
		return genericList.getList();
	}

	public GeneralDomain saveUser(GeneralDomain user) throws SQLException {
		GenericClass<GeneralDomain> genericClass = new GenericClass<>(user);
		GeneralSystemOperation<GenericClass<GeneralDomain>> sysOperation = new SaveUserSO();
		sysOperation.executeSystemOperation(genericClass);
		return  genericClass.getDomain();
	}

	public User saveProperty(PropertyWrapper propertyWraper) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SavePropertySO();
		sysOperation.executeSystemOperation(propertyWraper);
		return propertyWraper.getUser();
	}

	public PropertyWrapper returnPropertyForOwner(PropertyWrapper propertyOwner) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new ReturnUserPropertySO();
		sysOperation.executeSystemOperation(propertyOwner);
		return propertyOwner;
	}

}
