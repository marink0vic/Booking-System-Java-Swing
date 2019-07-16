package com.comtrade.broker;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;

public interface IBroker {

	GeneralDomain save(GeneralDomain domain) throws SQLException;
	void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException;
	List<GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException;
	PropertyWrapper returnPropertyForOwner(PropertyWrapper owner) throws SQLException;
	User login(User user) throws SQLException;
}
