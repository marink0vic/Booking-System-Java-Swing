package com.comtrade.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.comtrade.generics.Generic;

public interface GeneralDomain extends Generic {
	
	int returnIdNumber();
	String returnTableName();
	String returnColumnNames();
	String returnStatementPlaceholder();
	String returnIdColumnName();
	void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException;
}
