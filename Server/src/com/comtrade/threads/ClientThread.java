package com.comtrade.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread {
	
	private Socket socket;

	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
