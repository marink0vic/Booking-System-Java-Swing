package com.comtrade.domain.behavior;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.comtrade.generics.Generic;

public interface GeneralDomain extends Generic {
	
	int returnIdNumber();
	String returnTableName();
	String returnColumnNames();
	String returnStatementPlaceholder();
	String returnIdColumnName();
	void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException;
	List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException;
}
