package com.comtrade.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.communication.Communication;
import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.dto.UserWrapper;
import com.comtrade.generics.GenericMap;
import com.comtrade.transfer.TransferClass;

public class ControllerUI {

	private static ControllerUI controller;
	private List<Country> countryImages;
	private List<PaymentType> payments;
	private User user;
	private PropertyWrapper propertyWrapper;
	private List<PropertyWrapper> properties;
	private String messageResponse;
	
	private Map<Booking, List<BookedRoom>> bookedRooms;
	private PropertyWrapper hostReservationInfo;
	private UserWrapper userWrapper;
	
	private ControllerUI() {
		bookedRooms = new HashMap<>();
	}
	
	public static ControllerUI getController() {
		if (controller == null)
			controller = new ControllerUI();
		return controller;
	}

	public User getUser() {
		int counter = 0;
		while (user == null) {
			System.out.println("waiting for user " + counter);
			counter++;
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (counter == 3) break;
		}
		return user;
	}
	
	public UserWrapper getUserWrapper() {
		while (userWrapper == null) {
			System.out.println("Waiting for user bookings");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return userWrapper;
	}

	public PropertyWrapper getPropertyWrapper() {
		int counter = 0;
		while (propertyWrapper == null) {
			System.out.println("waiting for property wrapper " + counter);
			counter++;
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (counter == 3) break;
		}
		PropertyWrapper temp = propertyWrapper;
		propertyWrapper = null;
		return temp;
	}
	
	//OBRATI PAZNJU KADA SE ZOVE DRUGI PUT. NECE BITI NULL!!!!!!!
	public List<PropertyWrapper> getProperties() {
		while (properties == null) {
			System.out.println("Waiting for list of all properies");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	public Map<Booking, List<BookedRoom>> getBookedRooms() {
		return bookedRooms;
	}

	public PropertyWrapper getHostReservationInfo() {
		return hostReservationInfo;
	}

	public List<Country> getCountryImages() {
		while (countryImages == null) {
			System.out.println("Waiting for countries");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				
			}
		}
		return countryImages;
	}
	
	public List<PaymentType> getPayments() {
		while (payments == null) {
			System.out.println("waiting for payments");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				
			}
		}
		return payments;
	}

	public String getMessageResponse() {
		return messageResponse;
	}

	public void sendToServer(TransferClass transferClass) {
		Communication.getCommunication().send(transferClass);
	}

	@SuppressWarnings("unchecked")
	public void processCountryFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case RETURN_ALL:
		{
			countryImages = (List<Country>) transfer.getServerResponse();
			break;
		}
		default:
			break;
		}
		
	}

	public void processUserFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case SAVE:
		case LOGIN_USER:
		{
			user = (User) transfer.getServerResponse();
			messageResponse = transfer.getMessageResponse();
			break;
		}
		case RETURN_BOOKING_FOR_USER:
		{
			userWrapper = (UserWrapper) transfer.getServerResponse();
			break;
		}
		case UPDATE:
		{
			messageResponse = transfer.getMessageResponse();
		}
		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	public void processPaymentTypeFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case RETURN_ALL:
		{
			payments = (List<PaymentType>) transfer.getServerResponse();
			break;
		}
		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	public void processPropertyFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case SAVE_ALL_PROPERTY_INFO:
		case RETURN_PROPERTY_FOR_OWNER:
		{
			propertyWrapper = (PropertyWrapper) transfer.getServerResponse();
			break;
		}
		case RETURN_ALL:
		{
			properties = (List<PropertyWrapper>) transfer.getServerResponse();
			break;
		}
		default:
			break;
		}
	}

	public void processImagesFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case DELETE:
		{
			transfer.getServerResponse();
			break;
		}
		case SAVE:
		{
			propertyWrapper = (PropertyWrapper) transfer.getServerResponse();
			break;
		}

		default:
			break;
		}
	}

	public void processRoomsFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case SAVE:
		case UPDATE:
		{
			propertyWrapper = (PropertyWrapper) transfer.getServerResponse();
			break;
		}

		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	public void processBookingFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case SAVE:
		{
			propertyWrapper = (PropertyWrapper) transfer.getServerResponse();
			messageResponse = transfer.getMessageResponse();
			break;
		}
		case NOTIFY_ALL_USERS_WITH_NEW_BOOKINGS:
		{
			Map<Booking, List<BookedRoom>> temp = (Map<Booking, List<BookedRoom>>) transfer.getServerResponse();
			bookedRooms.putAll(temp);
			break;
		}
		case NOTIFY_HOST_WTIH_NEW_BOOKING:
		{
			hostReservationInfo = (PropertyWrapper) transfer.getServerResponse();
			break;
		}
		default:
			break;
		}
	}

	
}
