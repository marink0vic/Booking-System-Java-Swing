package com.comtrade.broker;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.RoomInfo;

public interface IBroker {

	void save(GeneralDomain domain) throws SQLException;
	void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException;
	List<GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException;
	GeneralDomain returnLastInsertedData(GeneralDomain domain) throws SQLException;
	List<GeneralDomain> returnLastInsertedData(GeneralDomain domain, int limit) throws SQLException;
}
