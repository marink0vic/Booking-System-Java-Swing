package com.comtrade.sysoperation.user;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveUserSO extends GeneralSystemOperation<GenericClass<GeneralDomain>> {

	@Override
	protected void executeSpecificOperation(GenericClass<GeneralDomain> user) throws SQLException {
		IBroker iBroker = new Broker();
		GeneralDomain domain = iBroker.save(user.getDomain());
		user.setDomain(domain);
	}

}
