package com.souchy.randd.ebishoal.sapphire.main;

import com.google.inject.AbstractModule;
//import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.ebishoal.sapphire.data.EbiCreatureData;
import com.souchy.randd.ebishoal.sapphire.data.EbiCreatureTypeI18N;
import com.souchy.randd.ebishoal.sapphire.data.EbiSpellData;

import data.CreatureData;
import data.SpellData;
import gamemechanics.creatures.CreatureType;
import gamemechanics.creatures.CreatureType.CreatureTypeI18N;

public class SapphireModule extends AbstractModule { // extends DefaultMechanicsModule ?

	@Override
	protected void configure() {
		// bind(ICell.class).to(EbiCell.class);
		
		bind(CreatureTypeI18N.class).to(EbiCreatureTypeI18N.class);
		bind(SpellData.class).to(EbiSpellData.class);
		bind(CreatureData.class).to(EbiCreatureData.class);
		
		requestStaticInjection(CreatureType.class);
	}
	

	// détermine les implémentations pour tous les eventhandlers, spells, etc
	// pour lier ça aux bons packets client->serveur et à l'affichage 3d

	// & lier le bon cache (json/ruby, pas de redis, pas de mongo)
	
	
}
