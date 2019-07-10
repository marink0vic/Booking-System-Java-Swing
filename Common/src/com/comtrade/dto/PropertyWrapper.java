package com.comtrade.dto;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.comtrade.domain.Address;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;

public class PropertyWrapper implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private User user;
	private Address address;
	private Property property;
	private Map<RoomType, List<Room>> rooms;
	private List<File> images; 
	private List<PaymentType> paymentList = new ArrayList<>();
	
	public PropertyWrapper(User user, Address address, Property property, Map<RoomType, List<Room>> rooms, List<File> images, List<PaymentType> paymentList) {
		this.user = user;
		this.address = address;
		this.property = property;
		this.rooms = rooms;
		this.images = images;
		this.paymentList = paymentList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Map<RoomType, List<Room>> getRooms() {
		return rooms;
	}

	public void setRooms(Map<RoomType, List<Room>> rooms) {
		this.rooms = rooms;
	}

	public List<File> getImages() {
		return images;
	}

	public void setImages(List<File> images) {
		this.images = images;
	}

	public List<PaymentType> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PaymentType> paymentList) {
		this.paymentList = paymentList;
	}
	
}
