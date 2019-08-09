package com.comtrade.sysoperation.booking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.Booking;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.dto.UserWrapper;
import com.comtrade.serverdata.ServerData;
import com.comtrade.serverdata.UserActiveThreads;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class UpdateBookingSO extends GeneralSystemOperation<PropertyWrapper> {

	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		List<Booking> bookings = new ArrayList<Booking>(wrapper.getBookings().keySet());
		
		IBroker iBroker = new Broker();
		iBroker.updateCollectionOfData(bookings);
		changeBookingStatus(bookings);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				updatePropertyInfoOnServer(bookings);
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				UserActiveThreads.getActiveThreads().notifyUsersForAcceptedBookings(bookings);
			}
		});
		thread.start();
		thread2.start();
	}

	private void changeBookingStatus(List<Booking> bookings) {
		for (Booking b : bookings) {
			b.setStatus("ACCEPTED");
		}
		
	}
	
	private void updatePropertyInfoOnServer(List<Booking> bookings) {
		int idProperty = bookings.get(0).getProperty().getIdProperty();
		for (PropertyWrapper wrapper : ServerData.getInstance().returnAllProperties()) {
			if (wrapper.getProperty().getIdProperty() == idProperty) {
				for (Booking b : bookings) {
					for (Booking b1 : wrapper.getBookings().keySet()) {
						if (b.getIdBooking() == b1.getIdBooking()) {
							b1.setStatus(b.getStatus());
							break;
						}
					}
				}
				break;
			}
		}
	}

}
