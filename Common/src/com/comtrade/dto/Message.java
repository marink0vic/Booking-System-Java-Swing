package com.comtrade.dto;

import java.io.Serializable;

import com.comtrade.domain.User;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private User sender;
	private User receiver;
	private String message;
	
	public Message() {
		
	}
	
	public Message(User sender, User receiver, String message) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
