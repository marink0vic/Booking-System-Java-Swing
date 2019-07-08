package com.comtrade.broker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.comtrade.connection.Connection;
import com.comtrade.domain.GeneralDomain;

public class Broker implements IBroker {

	@Override
	public List<GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException {
		String sql = "SELECT * FROM " + domain.returnTableName();
		List<GeneralDomain> list = new ArrayList<>();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		list = domain.returnList(resultSet);
		return list;
	}

}
