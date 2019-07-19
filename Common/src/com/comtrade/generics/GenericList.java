package com.comtrade.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericList<E> implements Generic {
	
	private List<E> list;
	
	public GenericList() {
		list = new ArrayList<>();
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public void listAddAll(List<E> list) {
		this.list.addAll(list);
	}
}
