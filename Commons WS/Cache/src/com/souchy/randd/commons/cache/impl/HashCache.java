package com.souchy.randd.commons.cache.impl;

import java.util.HashMap;

import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public class HashCache<K, V> implements Cache<K, V> {

	private HashMap<K, V> map;
	
	public HashCache() {
		map = new HashMap<>();
	}
	
	@Override
	public V get(K k) {
		return map.get(k);
	}

	@Override
	public void set(K k, V v) {
		map.put(k, v);
	}

	@Override
	public <T extends Identifiable<I>, I> T provide(I id) {
		return null;
	}

}
