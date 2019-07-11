package com.comtrade.broker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.comtrade.connection.Connection;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.Position;
import com.comtrade.domain.RoomInfo;

public class Broker implements IBroker {

	@Override
	public void save(GeneralDomain domain) throws SQLException {
		String sql = "INSERT INTO " + domain.returnTableName() + "" + domain.returnColumnNames() + "" + domain.returnStatementPlaceholder();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		Position index = new Position();
		domain.fillStatementWithValues(preparedStatement, index);
		preparedStatement.executeUpdate();
	}
	
	@Override
	public void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException {
		GeneralDomain domain = list.get(0);
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(domain.returnTableName()).append(domain.returnColumnNames());
		for (int i = 0; i < list.size(); i++) {
			sb.append(domain.returnStatementPlaceholder()).append(",");
		}
		
		String sql = sb.substring(0, sb.length() - 1);
		Position index = new Position();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).fillStatementWithValues(preparedStatement, index);
		}
		preparedStatement.executeUpdate();
	}
	
	@Override
	public List<GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException {
		String sql = "SELECT * FROM " + domain.returnTableName();
		List<GeneralDomain> list = new ArrayList<>();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		list = domain.returnList(resultSet);
		return list;
	}


	@Override
	public GeneralDomain returnLastInsertedData(GeneralDomain domain) throws SQLException {
		String sql = "SELECT * FROM " + domain.returnTableName() + " ORDER BY " + domain.returnIdColumnName()+" DESC LIMIT 1";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		GeneralDomain newDomain = domain.returnLastInsertedObject(resultSet);
		return newDomain;
	}


	@Override
	public List<GeneralDomain> returnLastInsertedData(GeneralDomain domain, int limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
