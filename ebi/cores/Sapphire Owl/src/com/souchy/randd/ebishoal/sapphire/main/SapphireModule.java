package com.souchy.randd.ebishoal.sapphire.main;

import com.google.inject.AbstractModule;
//import com.souchy.randd.jade.api.ICell;

public class SapphireModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind(ICell.class).to(EbiCell.class);
	}
	

	// détermine les implémentations pour tous les eventhandlers, spells, etc
	// pour lier ça aux bons packets client->serveur et à l'affichage 3d

	// & lier le bon cache (json/ruby, pas de redis, pas de mongo)
	
	
}
