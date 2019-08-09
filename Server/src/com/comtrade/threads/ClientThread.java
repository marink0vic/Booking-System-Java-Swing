package com.comtrade.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerBLBooking;
import com.comtrade.controller.ControllerBLCountry;
import com.comtrade.controller.ControllerBLImages;
import com.comtrade.controller.ControllerBLPaymentType;
import com.comtrade.controller.ControllerBLProperty;
import com.comtrade.controller.ControllerBLReview;
import com.comtrade.controller.ControllerBLRoom;
import com.comtrade.controller.ControllerBLUser;
import com.comtrade.controller.IControllerBL;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.serverdata.UserActiveThreads;
import com.comtrade.transfer.TransferClass;

public class ClientThread extends Thread {
	
	private Socket socket;

	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				try {
					TransferClass transferClass = (TransferClass) inputStream.readObject();
					processRequest(transferClass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				UserActiveThreads.getActiveThreads().removeThread(this);
				//e.printStackTrace();
				break;
			} 
		}
	}

	private void processRequest(TransferClass sender) {
		DomainType domainType = sender.getDomainType();
		TransferClass receiver = new TransferClass();
		IControllerBL controller = null;
		switch (domainType) {
		case COUNTRY:
		{
			controller = new ControllerBLCountry();
			break;
		}
		case PAYMENT_TYPE:
		{
			controller = new ControllerBLPaymentType();
			break;
		}
		case USER:
		{
			controller = new ControllerBLUser(this);
			break;
		}
		case PROPERTY:
		{
			controller = new ControllerBLProperty();
			break;
		} 
		case IMAGES:
		{
			controller = new ControllerBLImages();
			break;
		}
		case ROOM:
		{
			controller = new ControllerBLRoom();
			break;
		}
		case BOOKING:
		{
			controller = new ControllerBLBooking();
			break;
		}
		case REVIEW:
		{
			controller = new ControllerBLReview();
			break;
		}
		default:
			break;
		}
		receiver = controller.executeOperation(sender);
		sendResponse(receiver);
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void sendToHost(PropertyWrapper wrapper) {
		TransferClass transfer = new TransferClass();
		if (wrapper.getBookings().size() > 0) {
			transfer.setDomainType(DomainType.BOOKING);
			transfer.setOperation(Operations.NOTIFY_HOST_WTIH_NEW_BOOKING);
		} else if (wrapper.getReviews().size() > 0) {
			transfer.setDomainType(DomainType.REVIEW);
			transfer.setOperation(Operations.NOTIFY_HOST_WTIH_NEW_REVIEW);
		}
		transfer.setServerResponse(wrapper);
		sendResponse(transfer);
	}

	public void sendToUsers(Map<Booking, List<BookedRoom>> bookings) {
		TransferClass transfer = new TransferClass();
		transfer.setDomainType(DomainType.BOOKING);
		transfer.setOperation(Operations.NOTIFY_ALL_USERS_WITH_NEW_BOOKINGS);
		transfer.setServerResponse(bookings);
		sendResponse(transfer);
	}

	public void forwardMessage(User sender, String text_message) {
		TransferClass transfer = new TransferClass();
		transfer.setDomainType(DomainType.USER);
		transfer.setOperation(Operations.MESSAGE);
		transfer.setMessageResponse(text_message);
		transfer.setServerResponse(sender);
		sendResponse(transfer);
	}
	
	public void sendUpdatedBookingsToClient(List<Booking> booking_list) {
		TransferClass transfer = new TransferClass();
		transfer.setDomainType(DomainType.BOOKING);
		transfer.setOperation(Operations.NOTIFY_USER_WITH_ACCCEPTED_BOOKINGS);
		transfer.setServerResponse(booking_list);
		sendResponse(transfer);
	}
	
	private void sendResponse(TransferClass transfer) {
		try{
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(transfer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
