package com.comtrade.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connection {

	private static final Connection instance = new Connection();
	private java.sql.Connection connection;
	
	private Connection() {
		
	}
	
	public static Connection getConnection() {
		return instance;
	}
	
	public java.sql.Connection getSqlConnection() {
		return connection;
	}
	
	public void openConnection() {
		Properties properties = new Properties();
		readProperties(properties);
		String url = properties.getProperty("db.url");
		String user = properties.getProperty("db.user");
		String password = properties.getProperty("db.password");
		try {
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void confirmTransaction() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void canselTransaction() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void readProperties(Properties properties) {
		try(InputStream inputStream = new FileInputStream("./resources/application.properties")) {
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
