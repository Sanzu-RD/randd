package com.souchy.randd.situationtest.modules;

import com.google.inject.AbstractModule;
import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.situationtest.models.map.Cell;

public class DeathShadowsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ICell.class).to(Cell.class);
	}


	// d�termine les impl�mentations pour tous les eventhandlers, spells, etc
	// pour lier �a aux bons packets serveur->client et au cache
	
	// & lier le bon cache (redis + mongo + json/ruby)
	
}
