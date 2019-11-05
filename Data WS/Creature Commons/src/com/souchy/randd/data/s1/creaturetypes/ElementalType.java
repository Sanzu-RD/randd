package com.souchy.randd.data.s1.creaturetypes;

import com.souchy.randd.data.s1.main.Elements;

import data.new1.CreatureTypeModel;
import gamemechanics.statics.stats.modifiers.eleMod;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.properties.Resource;

public final class ElementalType extends CreatureTypeModel {
	
	public static final CreatureTypeModel inst = new ElementalType();
	
	private ElementalType() {
		// resources
		stats.add(400, Resource.life, mathMod.flat); 
		stats.add(8, Resource.mana, mathMod.flat); 
		stats.add(5, Resource.move, mathMod.flat); 
		// affinities
		// res
		
		// affinities and res have to be coded manually for each elemental type since they have different elements 
		// elementals have 50% base affinity and res in their main element
	}
	
}
