package com.comtrade.sysoperation;

import java.sql.SQLException;

import com.comtrade.connection.Connection;
import com.comtrade.generics.Generic;

public abstract class GeneralSystemOperation<T extends Generic> {
	
	public void executeSystemOperation(T object) throws SQLException{
		try {
			openConnection();
			executeSpecificOperation(object);
			confirmTransaction();
		} catch (Exception e) {
			canselTransaction();
			throw new SQLException();
		} finally {
			closeConnection();
		}
	}
	
	private void openConnection() {
		Connection.getConnection().openConnection();
	}
	private void confirmTransaction() {
		Connection.getConnection().confirmTransaction();
	}
	
	private void canselTransaction() {
		Connection.getConnection().canselTransaction();
	}
	private void closeConnection() {
		Connection.getConnection().closeConnection();
	}
	protected abstract void executeSpecificOperation(T object) throws SQLException;
}
