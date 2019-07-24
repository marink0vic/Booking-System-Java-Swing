package com.comtrade.sysoperation.booking;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Transaction;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveBookingSO extends GeneralSystemOperation<PropertyWrapper> {

	private IBroker iBroker = new Broker();
	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		Map.Entry<Booking, BookedRoom> entry = wrapper.getBookings().entrySet().iterator().next();
		Transaction transaction = wrapper.getTranactions().remove(0);
		
		Map<Booking, BookedRoom> savedBooking = saveBooking(entry.getKey(), entry.getValue(), transaction);
		wrapper.setBookings(savedBooking);
		
	}
	private Map<Booking, BookedRoom> saveBooking(Booking booking, BookedRoom room, Transaction transaction) throws SQLException {
		Map<Booking, BookedRoom> savedBooking = new HashMap<>();
		booking = (Booking) iBroker.save(booking);
		
		room.setIdBooking(booking.getIdBooking());
		room = (BookedRoom) iBroker.save(room);
		
		transaction.setIdBooking(booking.getIdBooking());
		iBroker.save(transaction);
		
		savedBooking.put(booking, room);
		return savedBooking;
	}

}
