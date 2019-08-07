package com.comtrade.domain.behavior;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DomainUpdate extends GeneralDomain {

	String returnColumnsForUpdate();
	void preparedStatementUpdate(PreparedStatement preparedStatement) throws SQLException;
}
