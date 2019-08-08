package com.comtrade.sysoperation.user;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.dto.UserWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class UserBookingSO extends GeneralSystemOperation<GenericClass<UserWrapper>> {

	@Override
	protected void executeSpecificOperation(GenericClass<UserWrapper> generic) throws SQLException {
		UserWrapper user = generic.getDomain();
		IBroker iBroker = new Broker();
		user.setBookings(iBroker.insertBookings(user.getUser(), user.getUser().getIdUser()));
//		for (UserWrapper wrapper : ServerData.getInstance().getUserBookings()) {
//			if (wrapper.getUser().getIdUser() == user.getUser().getIdUser()) {
//				generic.setDomain(wrapper);
//				break;
//			}
//		}
	}

}
