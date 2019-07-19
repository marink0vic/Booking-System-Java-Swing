package com.comtrade.generics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GenericMap<K,V> implements Generic, Serializable {

	
	private static final long serialVersionUID = 1L;
	private Map<K, V> map;
	
	public GenericMap() {
		map = new HashMap<>();
	}

	public Map<K, V> getMap() {
		return map;
	}

	public void setMap(Map<K, V> map) {
		this.map = map;
	}
	
	public void put(K key, V value) {
		map.put(key, value);
	}
	
	public V getValue(K key) {
		return map.get(key);
	}
	
	public K getKey() {
		Map.Entry<K, V> entry = map.entrySet().iterator().next();
		return entry.getKey();
	}
}
