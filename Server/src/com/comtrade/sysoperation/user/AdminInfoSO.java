package com.comtrade.sysoperation.user;

import java.sql.SQLException;

import com.comtrade.dto.AdminWrapper;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class AdminInfoSO extends GeneralSystemOperation<AdminWrapper> {

	@Override
	protected void executeSpecificOperation(AdminWrapper wrapper) throws SQLException {
		wrapper.setAllProperties(ServerData.getInstance().returnAllProperties());
		wrapper.setAllUsers(ServerData.getInstance().getUsers());
		wrapper.setTransactions(ServerData.getInstance().getTransactions());
	}

}
