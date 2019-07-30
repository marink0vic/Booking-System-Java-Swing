package com.comtrade.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DomainUpdate extends GeneralDomain {

	String returnColumnsForUpdate();
	void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException;
}
