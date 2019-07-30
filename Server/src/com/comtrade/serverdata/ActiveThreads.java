package com.comtrade.serverdata;


import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.threads.ClientThread;

public interface ActiveThreads {

	void register(User user, ClientThread thread);
	void removeThread(ClientThread clientThread);
	void notify(PropertyWrapper propertyWrapper);
	
}
