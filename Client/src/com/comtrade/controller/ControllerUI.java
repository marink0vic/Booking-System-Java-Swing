package com.comtrade.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.communication.Communication;
import com.comtrade.constants.Operations;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.User;
import com.comtrade.dto.Message;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.dto.UserWrapper;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.user.host.PropertyOwnerFrame;

public class ControllerUI {

	private static ControllerUI controller;
	private final Object messageLock = new Object();
	private final Object newAcceptedBookingsLock = new Object();
	
	private List<Country> countryImages;
	private List<PaymentType> payments;
	private User user;
	private PropertyWrapper propertyWrapper;
	private List<PropertyWrapper> properties;
	private String messageResponseFromServer;
	private Message chatMessage;
	
	private Map<Booking, List<BookedRoom>> bookedRooms;
	private List<Booking> acceptedBookings;
	private PropertyWrapper hostReservationInfo = new PropertyWrapper();
	private UserWrapper userWrapper;
	private PropertyOwnerFrame ownerFrame;
	
	private ControllerUI() {
		bookedRooms = new HashMap<>();
		chatMessage = new Message();
	}
	
	public static ControllerUI getController() {
		if (controller == null)
			controller = new ControllerUI();
		return controller;
	}
	

	public void setOwnerFrame(PropertyOwnerFrame ownerFrame) {
		this.ownerFrame = ownerFrame;
	}
	
	public PropertyWrapper getHostReservationInfo() {
		return hostReservationInfo;
	}
	
	public String getMessageResponse() {
		return messageResponseFromServer;
	}
	
	public Message getMessage() {
		synchronized (messageLock) {
			try {
				messageLock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return chatMessage;
	}
	
	public List<Booking> getAcceptedBookings() {
		synchronized (newAcceptedBookingsLock) {
			try {
				newAcceptedBookingsLock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return acceptedBookings;
	}

	public User getUser() {
		while (user == null) {
			System.out.println("waiting for user ");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		User temp = user;
		user = null;
		return temp;
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
	
	//OBRATI PAZNJU AKO SE ZOVE DRUGI PUT. NECE BITI NULL!!!!!!!
	public List<PropertyWrapper> getProperties() {
		while (properties == null) {
			System.out.println("Waiting for list of all properties");
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
			messageResponseFromServer = transfer.getMessageResponse();
			user = (User) transfer.getServerResponse();
			break;
		}
		case RETURN_BOOKING_FOR_USER:
		{
			userWrapper = (UserWrapper) transfer.getServerResponse();
			break;
		}
		case UPDATE:
		{
			messageResponseFromServer = transfer.getMessageResponse();
			break;
		}
		case MESSAGE:
		{
			User temp = (User) transfer.getServerResponse();
			chatMessage.setSender(temp);
			chatMessage.setMessage(transfer.getMessageResponse());
			synchronized (messageLock) {
				messageLock.notify();
			}
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
			messageResponseFromServer = transfer.getMessageResponse();
			break;
		}
		case UPDATE:
		{
			propertyWrapper = (PropertyWrapper) transfer.getServerResponse();
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
			ownerFrame.signalNewBooking(hostReservationInfo);
			break;
		}
		case NOTIFY_USER_WITH_ACCCEPTED_BOOKINGS:
		{
			acceptedBookings = (List<Booking>) transfer.getServerResponse();
			synchronized (newAcceptedBookingsLock) {
				newAcceptedBookingsLock.notify();
			}
			break;
		}
		default:
			break;
		}
	}

	public void processReviewFromServer(TransferClass transfer) {
		Operations operation = transfer.getOperation();
		switch (operation) {
		case NOTIFY_HOST_WTIH_NEW_REVIEW:
		{
			PropertyWrapper temp = (PropertyWrapper) transfer.getServerResponse();
			ownerFrame.signalNewReview(temp.getReviews().get(0));
			break;
		}

		default:
			break;
		}
	}
	
}
