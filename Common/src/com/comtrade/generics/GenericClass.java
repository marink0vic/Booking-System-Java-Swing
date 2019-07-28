package com.comtrade.generics;


public class GenericClass<T extends Generic> implements Generic {

	private T domain;

	public GenericClass(T domain) {
		this.domain = domain;
	}

	public T getDomain() {
		return domain;
	}

	public void setDomain(T domain) {
		this.domain = domain;
	}

	
	
}
