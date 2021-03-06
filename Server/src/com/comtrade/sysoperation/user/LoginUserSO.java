package com.comtrade.sysoperation.user;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.User;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class LoginUserSO extends GeneralSystemOperation<GenericClass<User>> {

	@Override
	protected void executeSpecificOperation(GenericClass<User> domain) throws SQLException {
		User user = domain.getDomain();
		IBroker iBroker = new Broker();
		User newUser = iBroker.login(user);
		domain.setDomain(newUser);
	}

}
