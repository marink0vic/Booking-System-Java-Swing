package com.comtrade.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DomainList extends GeneralDomain {
	List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException;
}
