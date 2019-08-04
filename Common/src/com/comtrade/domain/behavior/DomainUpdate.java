package com.comtrade.domain.behavior;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.comtrade.domain.Position;

public interface DomainUpdate extends GeneralDomain {

	String returnColumnsForUpdate();
	void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException;
}
