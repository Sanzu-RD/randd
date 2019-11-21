package com.souchy.randd.data.s1.creaturetypes;

import com.souchy.randd.data.s1.main.Elements;

import data.new1.CreatureTypeModel;
import gamemechanics.statics.stats.modifiers.eleMod;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.properties.Resource;

public final class KnightType extends CreatureTypeModel {
	
	public static final CreatureTypeModel inst = new KnightType();
	
	private KnightType() {
		// resources
		stats.addResource(1000, Resource.life); 
		stats.addResource(100, Resource.mana); 
		stats.addResource(6, Resource.move); 
		// affinities
		stats.addAffinity(25, Elements.steel);
		// res
		stats.addResistance(25, Elements.steel);
		stats.addResistance(25, Elements.physical);
		

		// passive is flat resistance
		stats.add(20, Elements.global, eleMod.res, mathMod.flat);
	}
	
}
