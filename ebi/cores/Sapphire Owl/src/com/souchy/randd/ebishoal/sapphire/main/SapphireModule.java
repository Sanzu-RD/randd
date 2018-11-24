package com.souchy.randd.ebishoal.sapphire.main;

import com.google.inject.AbstractModule;
import com.souchy.randd.ebishoal.sapphire.models.EbiCell;
import com.souchy.randd.jade.api.ICell;

public class SapphireModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ICell.class).to(EbiCell.class);
	}
	

	// d�termine les impl�mentations pour tous les eventhandlers, spells, etc
	// pour lier �a aux bons packets client->serveur et � l'affichage 3d

	// & lier le bon cache (json/ruby, pas de redis, pas de mongo)
	
	
}
