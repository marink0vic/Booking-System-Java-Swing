package com.comtrade.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.comtrade.dto.PropertyWrapper;

public interface DomainJoin {
	String prepareJoin() throws SQLException;
	PropertyWrapper returnJoinTables(ResultSet rs) throws SQLException;
}
