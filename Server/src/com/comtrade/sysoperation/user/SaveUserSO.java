package com.comtrade.sysoperation.user;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.User;
import com.comtrade.domain.behavior.GeneralDomain;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveUserSO extends GeneralSystemOperation<GenericClass<User>> {

	@Override
	protected void executeSpecificOperation(GenericClass<User> generic) throws SQLException {
		IBroker iBroker = new Broker();
		User user = generic.getDomain();
		int id = iBroker.save(user);
		user.setIdUser(id);
	}

}
