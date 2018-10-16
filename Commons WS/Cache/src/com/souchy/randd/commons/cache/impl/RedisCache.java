package com.souchy.randd.commons.cache.impl;

import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public class RedisCache implements Cache<String, String> {
	

	@Override
	public String get(String key) {
		return "";
	}

	public int getInt(String key) {
		return 0;
	}

	@Override
	public void set(String k, String v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends Identifiable<I>, I> T provide(I id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
