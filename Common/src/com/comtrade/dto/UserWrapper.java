package com.comtrade.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.User;
import com.comtrade.generics.Generic;

public class UserWrapper implements Generic, Serializable {

	
	private static final long serialVersionUID = 1L;
	private User user;
	private Map<Booking, List<BookedRoom>> bookings;
	
	public UserWrapper() {
		bookings = new HashMap<>();
	}

	public UserWrapper(User user, Map<Booking, List<BookedRoom>> bookings) {
		super();
		this.user = user;
		this.bookings = bookings;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<Booking, List<BookedRoom>> getBookings() {
		return bookings;
	}

	public void setBookings(Map<Booking, List<BookedRoom>> bookings) {
		this.bookings = bookings;
	}
	
	public void addNewBooking(Booking booking, List<BookedRoom> bookedRoom) {
		bookings.put(booking, bookedRoom);
	}

}
