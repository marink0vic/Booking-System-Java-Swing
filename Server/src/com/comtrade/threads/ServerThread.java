package com.comtrade.threads;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class ServerThread extends Thread {
	
	private Integer PORT;

	@Override
	public void run() {
		try {
			setPort();
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				ClientThread client = new ClientThread();
				client.setSocket(socket);
				client.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
