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
	void fillStatementWithValues(PreparedStatement preparedStatement, Position index) throws SQLException;
	List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException;
	String returnIdColumnName();
	GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException;
}
