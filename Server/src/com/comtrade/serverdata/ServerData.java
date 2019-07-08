package com.comtrade.serverdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.connection.Connection;
import com.comtrade.domain.Country;
import com.comtrade.domain.GeneralDomain;

public class ServerData {
	private List<GeneralDomain> countries;
	private final String MAIN_FOLDER_PATH = "C:\\Users\\marko\\Desktop\\Booking-System-Java-Swing\\Server";
	private static final ServerData serverData = new ServerData();
	
	private ServerData() {
		
	}
	
	public static ServerData getInstance() {
		return serverData;
	}
	
	public void loadData() throws SQLException {
		Connection.getConnection().openConnection();
		IBroker iBroker = new Broker();
		countries = iBroker.returnAllData(new Country());
		Connection.getConnection().closeConnection();
		addFullPath(countries);
	}
	
	
	public synchronized List<GeneralDomain> returnListOfCountries() {
		List<GeneralDomain> copy = new ArrayList<>();
		copy.addAll(countries);
		return copy;
	}
	
	private void addFullPath(List<GeneralDomain> domainList) {
		if (domainList.get(0) instanceof Country) {
			for (int i = 0; i < domainList.size(); i++) {
				Country country = (Country) domainList.get(i);
				String fullPath = MAIN_FOLDER_PATH + country.getImage();
				country.setImage(fullPath);
			}
		} 
	}
}
