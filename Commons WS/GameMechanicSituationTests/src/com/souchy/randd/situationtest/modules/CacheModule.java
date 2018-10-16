package com.souchy.randd.situationtest.modules;

import com.google.inject.AbstractModule;
import com.souchy.randd.commons.cache.api.Cache;
import com.souchy.randd.commons.cache.impl.HashCache;




/**
 * 
 * TODO � delete et � mettre dans DeathShadowsModule et dans EbiShoalModule
 * 
 * @author Souchy
 *
 */
public class CacheModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Cache.class).to(HashCache.class);
	}

}
