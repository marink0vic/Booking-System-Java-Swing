package com.comtrade.generics;

import com.comtrade.domain.GeneralDomain;

public class GenericClass<T extends GeneralDomain> implements Generic {

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
