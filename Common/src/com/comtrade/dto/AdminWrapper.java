package com.comtrade.dto;

import java.io.Serializable;
import java.util.List;

import com.comtrade.domain.Transaction;
import com.comtrade.domain.User;
import com.comtrade.generics.Generic;

public class AdminWrapper implements Serializable, Generic {
	
	private static final long serialVersionUID = 1L;
	private List<User> allUsers;
	private List<PropertyWrapper> allProperties;
	private List<Transaction> transactions;
	
	public AdminWrapper() {
		
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	public List<PropertyWrapper> getAllProperties() {
		return allProperties;
	}

	public void setAllProperties(List<PropertyWrapper> allProperties) {
		this.allProperties = allProperties;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
}
