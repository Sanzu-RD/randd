package com.souchy.randd.commons.cache.impl;

import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public class MongoCache<K, V> implements Cache<K,V> {

	@Override
	public V get(K k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(K k, V v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends Identifiable<I>, I> T provide(I id) {
		// TODO Auto-generated method stub
		return null;
	}

}
