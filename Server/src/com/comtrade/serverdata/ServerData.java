package com.comtrade.serverdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.connection.Connection;
import com.comtrade.domain.Country;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PaymentType;

public class ServerData {
	private List<GeneralDomain> countries;
	private List<GeneralDomain> paymentTypes;
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
		paymentTypes = iBroker.returnAllData(new PaymentType());
		Connection.getConnection().closeConnection();
		addFullPath(countries);
		addFullPath(paymentTypes);
	}
	
	
	public synchronized List<GeneralDomain> returnListOfCountries() {
		List<GeneralDomain> copy = new ArrayList<>();
		copy.addAll(countries);
		return copy;
	}
	
	public List<GeneralDomain> returnListOfPaymentTypes() {
		List<GeneralDomain> copy = new ArrayList<>();
		copy.addAll(paymentTypes);
		return copy;
	}
	
	private void addFullPath(List<GeneralDomain> domainList) {
		if (domainList.get(0) instanceof Country) {
			for (int i = 0; i < domainList.size(); i++) {
				Country country = (Country) domainList.get(i);
				String fullPath = MAIN_FOLDER_PATH + country.getImage();
				country.setImage(fullPath);
			}
		} else if (domainList.get(0) instanceof PaymentType) {
			for (int i = 0; i < domainList.size(); i++) {
				PaymentType payment = (PaymentType) domainList.get(i);
				String fullPath = MAIN_FOLDER_PATH + payment.getImage();
				payment.setImage(fullPath);
			}
		}
	}

}
