package com.souchy.randd.commons.cache.api;

import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public interface Cache<K, V> {

	
	public V get(K k);
	
	public void set(K k, V v);
	
	
	public <T extends Identifiable<I>, I> T provide(I id);
	
	
}
