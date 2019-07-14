package com.comtrade.dto;

import java.io.Serializable;

import com.comtrade.domain.Country;
import com.comtrade.domain.User;
import com.comtrade.generics.Generic;

public class PropertyWrapper implements Serializable, Generic {

	private static final long serialVersionUID = 1L;
	private User user;
	private Country country;
	
	public PropertyWrapper() {
		
	}

	public PropertyWrapper(User user, Country country) {
		super();
		this.user = user;
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}
