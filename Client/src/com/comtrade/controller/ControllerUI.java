package com.comtrade.controller;

import java.io.IOException;
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
import com.comtrade.generics.GenericMap;
import com.comtrade.transfer.TransferClass;

public class ControllerUI {

	private static ControllerUI controller;
	private List<Country> countryImages;
	private List<PaymentType> payments;
	private User user;
	private PropertyWrapper propertyWrapper;
	
	private ControllerUI() {
		
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
			if (counter == 4) break;
		}
		return user;
	}
	
	public PropertyWrapper getPropertyWrapper() {
		while (propertyWrapper == null) {
			System.out.println("waiting for property wrapper");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		PropertyWrapper temp = propertyWrapper;
		propertyWrapper = null;
		return temp;
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

	public void sendToServer(TransferClass transferClass) {
		Communication.getCommunication().send(transferClass);
	}
	
	
	public TransferClass returnAllProperties() throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(transferClass);
		transferClass.setDomainType(DomainType.PROPERTY);
		transferClass.setOperation(Operations.RETURN_ALL);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass saveBooking(PropertyWrapper propertyWrapper) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(propertyWrapper);
		transferClass.setDomainType(DomainType.BOOKING);
		transferClass.setOperation(Operations.SAVE);
		return sendAndReturn(transferClass);
	}
	public TransferClass returnBookingsForProperty(PropertyWrapper propertyWrapper) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(propertyWrapper);
		transferClass.setDomainType(DomainType.BOOKING);
		transferClass.setOperation(Operations.RETURN_BOOKING_FOR_PROPERTY);
		return sendAndReturn(transferClass);
	}
	
	private TransferClass sendAndReturn(TransferClass transferClass) throws ClassNotFoundException, IOException {
		Communication.getCommunication().send(transferClass);
		TransferClass transferClass2 = Communication.getCommunication().read();
		return transferClass2;
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
			break;
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

	public void processPropertyFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case SAVE_ALL_PROPERTY_INFO:
		case RETURN_PROPERTY_FOR_OWNER:
		{
			propertyWrapper = (PropertyWrapper) transfer.getServerResponse();
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

	
}
