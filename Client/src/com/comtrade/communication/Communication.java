package com.comtrade.communication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import com.comtrade.transfer.TransferClass;

public class Communication {

	private static Communication communication;
	private Socket socket;
	
	private Communication() {
		try {
			Properties properties = new Properties();
			readProperties(properties);
			String serverUrl = properties.getProperty("server.url");
			int serverPort = Integer.parseInt(properties.getProperty("server.port"));
			socket = new Socket(serverUrl, serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Communication getCommunication() {
		if (communication == null)
			communication = new Communication();
		return communication;
	}
	
	public void send(TransferClass transferClass) {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(transferClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TransferClass read() throws IOException, ClassNotFoundException {
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		return (TransferClass) objectInputStream.readObject();
	}
	
	private void readProperties(Properties property) {
		try(InputStream inputStream = new FileInputStream("./resources/application.properties")){
			property.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
