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
	
//	public TransferClass returnPaymentList() throws ClassNotFoundException, IOException {
//		TransferClass transferClass = new TransferClass();
//		transferClass.setDomainType(DomainType.PAYMENT_TYPE);
//		transferClass.setOperation(Operations.RETURN_ALL);
//		return sendAndReturn(transferClass);
//	}
	
	public TransferClass saveProperty(PropertyWrapper propertyOwner) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(propertyOwner);
		transferClass.setDomainType(DomainType.PROPERTY);
		transferClass.setOperation(Operations.SAVE_ALL_PROPERTY_INFO);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass returnPropertyForOwner(PropertyWrapper propertyOwner) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(propertyOwner);
		transferClass.setDomainType(DomainType.PROPERTY);
		transferClass.setOperation(Operations.RETURN_PROPERTY_FOR_OWNER);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass returnUser(User user) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(user);
		transferClass.setDomainType(DomainType.USER);
		transferClass.setOperation(Operations.LOGIN_USER);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass saveImages(PropertyWrapper wrapper) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(wrapper);
		transferClass.setDomainType(DomainType.IMAGES);
		transferClass.setOperation(Operations.SAVE);
		return sendAndReturn(transferClass);
	}
	
	public void deleteImages(List<PropertyImage> imagesForDeletion) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(imagesForDeletion);
		transferClass.setDomainType(DomainType.IMAGES);
		transferClass.setOperation(Operations.DELETE);
		sendAndReturn(transferClass);
	}
	
	public TransferClass saveRoom(PropertyWrapper tempOwner) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(tempOwner);
		transferClass.setDomainType(DomainType.ROOM);
		transferClass.setOperation(Operations.SAVE);
		return sendAndReturn(transferClass);
	}
	
	public TransferClass updateRoom(PropertyWrapper tempOwner) throws ClassNotFoundException, IOException {
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(tempOwner);
		transferClass.setDomainType(DomainType.ROOM);
		transferClass.setOperation(Operations.UPDATE);
		return sendAndReturn(transferClass);
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

	
}
