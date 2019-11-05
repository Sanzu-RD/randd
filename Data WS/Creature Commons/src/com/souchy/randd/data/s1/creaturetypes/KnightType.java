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
		stats.add(600, Resource.life, mathMod.flat); 
		stats.add(8, Resource.mana, mathMod.flat); 
		stats.add(4, Resource.move, mathMod.flat); 
		// affinities
		stats.add(25, Elements.steel, eleMod.affinity, mathMod.scl);
		// res
		stats.add(25, Elements.steel, eleMod.res, mathMod.scl);
		stats.add(25, Elements.physical, eleMod.res, mathMod.scl);
	}
	
}
