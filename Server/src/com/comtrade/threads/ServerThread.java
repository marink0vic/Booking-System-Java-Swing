package com.comtrade.threads;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;

import com.comtrade.serverdata.ServerData;

public class ServerThread extends Thread {
	
	private Integer PORT;

	@Override
	public void run() {
		try {
			setPort();
			loadServerData();
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				ClientThread client = new ClientThread();
				client.setSocket(socket);
				client.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadServerData() {
		try {
			ServerData.getInstance().loadData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void setPort() {
		Properties properties = new Properties();
		try(InputStream inputStream = new FileInputStream("./resources/application.properties")) {
			properties.load(inputStream);
			PORT = Integer.parseInt(properties.getProperty("server.port"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
