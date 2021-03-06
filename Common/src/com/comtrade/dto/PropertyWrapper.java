package com.comtrade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.comtrade.domain.Location;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.Transaction;
import com.comtrade.domain.User;
import com.comtrade.generics.Generic;

public class PropertyWrapper implements Serializable, Generic {

	private static final long serialVersionUID = 1L;
	private User user;
	private Location address;
	private Property property;
	private Map<RoomType, Room> rooms;
	private List<PropertyImage> images; 
	private List<PaymentType> paymentList;
	private List<Transaction> transactions;
	private Map<Booking, List<BookedRoom>> bookings;
	private List<PropertyReview> reviews;
	private Country country;
	
	public PropertyWrapper() {
		bookings = new HashMap<>();
		transactions = new ArrayList<>();
		reviews = new ArrayList<>();
	}
	
	public PropertyWrapper(User user, Location address, 
						   Property property, Map<RoomType, Room> rooms, 
						   List<PropertyImage> images, List<PaymentType> paymentList) 
	{
		this.user = user;
		this.address = address;
		this.property = property;
		this.rooms = rooms;
		this.images = images;
		this.paymentList = paymentList;
		bookings = new HashMap<>();
		transactions = new ArrayList<>();
		reviews = new ArrayList<>();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Location getAddress() {
		return address;
	}

	public void setAddress(Location address) {
		this.address = address;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Map<RoomType, Room> getRooms() {
		return rooms;
	}

	public void setRooms(Map<RoomType, Room> rooms) {
		this.rooms = rooms;
	}

	public List<PropertyImage> getImages() {
		return images;
	}

	public void setImages(List<PropertyImage> images) {
		this.images = images;
	}

	public List<PaymentType> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PaymentType> paymentList) {
		this.paymentList = paymentList;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Map<Booking, List<BookedRoom>> getBookings() {
		return bookings;
	}

	public void setBookings(Map<Booking, List<BookedRoom>> bookings) {
		this.bookings = bookings;
	}
	
	public List<PropertyReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<PropertyReview> reviews) {
		this.reviews = reviews;
	}

	//------
	public void addNewBooking(Booking booking, List<BookedRoom> bookedRoom) {
		bookings.put(booking, bookedRoom);
	}
	
	public void addNewTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public void addNewRoom(RoomType type, Room room) {
		rooms.put(type, room);
	}
	
	public void addNewReview(PropertyReview review) {
		reviews.add(review);
	}

}
