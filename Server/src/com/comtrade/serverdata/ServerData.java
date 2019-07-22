package com.comtrade.serverdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.connection.Connection;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;

public class ServerData {
	private List<Country> countries;
	private List<PaymentType> paymentTypes;
	private List<User> allUsers;
	private Map<User, PropertyWrapper> propertyMap;
	private IBroker iBroker;
	private static final ServerData serverData = new ServerData();
	
	private ServerData() {
		iBroker = new Broker();
		propertyMap = new HashMap<>();
	}
	
	public static ServerData getInstance() {
		return serverData;
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() throws SQLException {
		
		Connection.getConnection().openConnection();
		
		countries = (List<Country>) iBroker.returnAllData(new Country());
		paymentTypes = (List<PaymentType>) iBroker.returnAllData(new PaymentType());
		allUsers = (List<User>) iBroker.returnAllData(new User());
		addAllProperties();
		
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
	
	public Map<User, PropertyWrapper> returnAllProperties() {
		return Collections.unmodifiableMap(propertyMap);
	}
	
	private void addAllProperties() throws SQLException {
		for (User user : allUsers) {
			if (user.getStatus().equals("SUPER_USER")) {
				PropertyWrapper temp = new PropertyWrapper();
				temp.setUserID(user.getIdUser());
				iBroker.insertPropertyForOwner(temp);
				propertyMap.put(user, temp);
			}
		}
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
