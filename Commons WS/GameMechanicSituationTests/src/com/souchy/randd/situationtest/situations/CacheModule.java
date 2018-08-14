package com.souchy.randd.situationtest.situations;

import com.google.inject.AbstractModule;
import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.commons.cache.impl.HashCache;

public class CacheModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Cache.class).to(HashCache.class);
	}

}
