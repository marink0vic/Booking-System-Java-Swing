package com.comtrade.domain.behavior;

import java.sql.SQLException;

public interface DomainJoin {
	String prepareJoin() throws SQLException;
}
