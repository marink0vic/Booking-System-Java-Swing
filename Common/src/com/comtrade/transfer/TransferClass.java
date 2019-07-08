package com.comtrade.transfer;

import java.io.Serializable;

import com.comtrade.constants.Operations;

public class TransferClass implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object clientRequest;
	private Object serverResponse;
	private String messageResponse;
	private Operations operation;
	
	public Object getClientRequest() {
		return clientRequest;
	}
	public void setClientRequest(Object clientRequest) {
		this.clientRequest = clientRequest;
	}
	public Object getServerResponse() {
		return serverResponse;
	}
	public void setServerResponse(Object serverResponse) {
		this.serverResponse = serverResponse;
	}
	public String getMessageResponse() {
		return messageResponse;
	}
	public void setMessageResponse(String messageResponse) {
		this.messageResponse = messageResponse;
	}
	public Operations getOperation() {
		return operation;
	}
	public void setOperation(Operations operation) {
		this.operation = operation;
	}

}
