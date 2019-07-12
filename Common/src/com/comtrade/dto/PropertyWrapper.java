package com.comtrade.dto;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.comtrade.domain.Address;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.generics.Generic;

public class PropertyWrapper implements Serializable, Generic {

	
	private static final long serialVersionUID = 1L;
	private User user;
	private Address address;
	private Property property;
	private Map<RoomType, RoomInfo> room;
	private List<PropertyImage> images; 
	private List<PaymentType> paymentList = new ArrayList<>();
	
	public PropertyWrapper(User user, Address address, Property property, Map<RoomType, RoomInfo> room, List<PropertyImage> images, List<PaymentType> paymentList) {
		this.user = user;
		this.address = address;
		this.property = property;
		this.room = room;
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

	public Map<RoomType, RoomInfo> getRoom() {
		return room;
	}

	public void setRoom(Map<RoomType, RoomInfo> room) {
		this.room = room;
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
	
}
