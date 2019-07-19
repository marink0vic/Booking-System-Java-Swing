package com.comtrade.serverdata;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.connection.Connection;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;

public class ServerData {
	private List<Country> countries;
	private List<PaymentType> paymentTypes;
	private static final ServerData serverData = new ServerData();
	
	private ServerData() {
		
	}
	
	public static ServerData getInstance() {
		return serverData;
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() throws SQLException {
		
		Connection.getConnection().openConnection();
		IBroker iBroker = new Broker();
		countries = (List<Country>) iBroker.returnAllData(new Country());
		paymentTypes = (List<PaymentType>) iBroker.returnAllData(new PaymentType());
		Connection.getConnection().closeConnection();
		
		addFullCountryImagePath();
		addFullPaymentImagePath();
	}
	
	
	public List<Country> returnListOfCountries() {
		return Collections.unmodifiableList(countries);
	}
	
	public List<PaymentType> returnListOfPaymentTypes() {
		return Collections.unmodifiableList(paymentTypes);
	}
	
	private void addFullCountryImagePath() {
		for (Country country : countries) {
			String fullPath = ImageFolder.SERVER_RESOURCES_PATH.getPath() + country.getImage();
			country.setImage(fullPath);
		}
	}
	
	private void addFullPaymentImagePath() {
		for (PaymentType payment : paymentTypes) {
			String fullPath = ImageFolder.SERVER_RESOURCES_PATH.getPath() + payment.getImage();
			payment.setImage(fullPath);
		}
	}

}
