package com.comtrade.broker;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.domain.GeneralDomain;

public interface IBroker {

	List<GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException;
}
