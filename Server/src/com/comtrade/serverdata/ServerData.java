package com.comtrade.serverdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.IOP.TransactionService;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.connection.Connection;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.Transaction;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericList;

public class ServerData {
	private List<Country> countries;
	private List<PaymentType> paymentTypes;
	private List<User> superUsers;
	private List<User> regularUsers;
	private List<PropertyWrapper> properties;
	private List<Transaction> transactions;
	private IBroker iBroker;
	private static final ServerData serverData = new ServerData();
	
	private ServerData() {
		iBroker = new Broker();
		properties = new ArrayList<>();
		transactions = new ArrayList<>();
	}
	
	public static ServerData getInstance() {
		return serverData;
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() throws SQLException {
		
		Connection.getConnection().openConnection();
		
		countries = (List<Country>) iBroker.returnAllData(new Country());
		paymentTypes = (List<PaymentType>) iBroker.returnAllData(new PaymentType());
		superUsers = iBroker.returnUsers(new User(), "SUPER_USER");
		regularUsers = iBroker.returnUsers(new User(), "USER");
		transactions = (List<Transaction>) iBroker.returnAllData(new Transaction());
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
	
	public List<PropertyWrapper> returnAllProperties() {
		return properties;
	}
	
	public void addNewProperty(PropertyWrapper property) {
		properties.add(property);
	}
	
	public void addNewTransaction(Transaction transaction) {
		transactions.add(transaction);
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

}
