package com.comtrade.generics;

import com.comtrade.domain.GeneralDomain;

public class GenericClass<T extends GeneralDomain> implements Generic {

	private GeneralDomain T;

	public GenericClass(GeneralDomain t) {
		T = t;
	}

	public GeneralDomain getDomain() {
		return T;
	}

	public void setDomain(GeneralDomain t) {
		T = t;
	}
	
}
