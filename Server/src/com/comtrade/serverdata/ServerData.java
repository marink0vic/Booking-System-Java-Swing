package com.comtrade.serverdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.connection.Connection;
import com.comtrade.constants.ImageFolder;
import com.comtrade.constants.UserType;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.Transaction;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.dto.UserWrapper;

public class ServerData {
	private List<Country> countries;
	private List<PaymentType> paymentTypes;
	private List<User> superUsers;
	private List<User> regularUsers;
	private List<PropertyWrapper> properties;
	private List<Transaction> transactions;
	private List<UserWrapper> userBookings;
	private IBroker iBroker;
	private static final ServerData serverData = new ServerData();
	
	private ServerData() {
		iBroker = new Broker();
		properties = new ArrayList<>();
		transactions = new ArrayList<>();
		userBookings = new ArrayList<>();
	}
	
	public static ServerData getInstance() {
		return serverData;
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() throws SQLException {
		
		Connection.getConnection().openConnection();
		
		countries = (List<Country>) iBroker.returnAllData(new Country());
		paymentTypes = (List<PaymentType>) iBroker.returnAllData(new PaymentType());
		superUsers = iBroker.returnUsers(new User(), UserType.SUPER_USER.getAccess());
		regularUsers = iBroker.returnUsers(new User(), UserType.USER.getAccess());
		transactions = (List<Transaction>) iBroker.returnAllData(new Transaction());
		addAllProperties();
		addAllUserBookings();
		
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
	
	public List<PropertyWrapper> returnAllProperties() {
		return properties;
	}
	
	public List<UserWrapper> getUserBookings() {
		return userBookings;
	}

	public void addNewProperty(PropertyWrapper property) {
		properties.add(property);
	}
	
	public void addNewTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public void addNewUser(User user) {
		if (user.getStatus().equals(UserType.USER.getAccess())) {
			regularUsers.add(user);
		} else if (user.getStatus().equals(UserType.SUPER_USER.getAccess())) {
			superUsers.add(user);
		}
	}
	
	public Map<Booking, List<BookedRoom>> returnBookingsForProperty(Property property) {
		for (PropertyWrapper pw : properties) {
			if (pw.getProperty().getIdProperty() == property.getIdProperty()) {
				return pw.getBookings();
			}
		}
		return null;
	}
	
	private void addAllProperties() throws SQLException {
		for (User user : superUsers) {
			PropertyWrapper temp = new PropertyWrapper();
			temp.setUser(user);
			iBroker.insertPropertyForOwner(temp);
			temp.setTransactions(addAllTransactions(temp.getProperty()));
			properties.add(temp);
		}
	}
	
	private void addAllUserBookings() throws SQLException {
		for (User user : regularUsers) {
			UserWrapper temp = new UserWrapper();
			temp.setUser(user);
			temp.setBookings(iBroker.insertBookings(user, user.getIdUser()));
			userBookings.add(temp);
		}
	}
	private List<Transaction> addAllTransactions(Property temp) {
		List<Transaction> list = new ArrayList<>();
		for (Transaction transaction : transactions) {
			if(transaction.getIdReceiver() == temp.getIdProperty()) {
				list.add(transaction);
			}
		}
		return list;
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

	public boolean existsInDatabase(User user) {
		for (User u : regularUsers) {
			if (u.getUsername().equals(user.getUsername()) || u.getEmail().equals(user.getEmail())) {
				return true;
			}
		} 
		for (User u : superUsers) {
			if (u.getUsername().equals(user.getUsername()) || u.getEmail().equals(user.getEmail())) {
				return true;
			}
		}
		return false;
	}


}
