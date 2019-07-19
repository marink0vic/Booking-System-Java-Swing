package com.comtrade.domain;

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
	String returnColumnsForUpdate();
	String returnIdColumnName();
	void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException;
	void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException;
	List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException;
	GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException;
}
