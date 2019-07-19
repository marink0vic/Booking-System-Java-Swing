package com.comtrade.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.comtrade.domain.Address;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.generics.Generic;

public class PropertyWrapper implements Serializable, Generic {

	
	private static final long serialVersionUID = 1L;
	private int userID;
	private Address address;
	private Property property;
	private Map<RoomType, RoomInfo> room;
	private List<PropertyImage> images; 
	private List<PaymentType> paymentList;
	private Country country;
	
	public PropertyWrapper() {
	}
	
	public PropertyWrapper(int userID, Address address, Property property, Map<RoomType, RoomInfo> room, List<PropertyImage> images, List<PaymentType> paymentList) {
		this.userID = userID;
		this.address = address;
		this.property = property;
		this.room = room;
		this.images = images;
		this.paymentList = paymentList;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}
