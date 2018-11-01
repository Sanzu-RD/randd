package com.souchy.randd.commons.cache.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public abstract class JSONCache<O> implements Cache<String, O> {

	protected Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	//zxc pour json configs .... ça ou utilise PropertyConfig .....
	@Override
	public O get(String k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(String k, O v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends Identifiable<I>, I> T provide(I id) {
		// TODO Auto-generated method stub
		return null;
	}

}
