package com.souchy.randd.data.s1.creaturetypes;

import com.souchy.randd.data.s1.main.Elements;

import data.new1.CreatureTypeModel;
import gamemechanics.statics.stats.modifiers.eleMod;
import gamemechanics.statics.stats.modifiers.mathMod;
import gamemechanics.statics.stats.properties.Resource;

public class SkeletonType extends CreatureTypeModel {

	public static final CreatureTypeModel inst = new SkeletonType();
	
	private SkeletonType() {
		// resources
		stats.add(500, Resource.life, mathMod.flat); 
		stats.add(8, Resource.mana, mathMod.flat); 
		stats.add(5, Resource.move, mathMod.flat); 
		// affinities
		stats.add(25, Elements.dark, eleMod.affinity, mathMod.scl);
		// res
		stats.add(25, Elements.dark, eleMod.res, mathMod.scl);
		stats.add(25, Elements.physical, eleMod.res, mathMod.scl);
		stats.add(25, Elements.electricity, eleMod.res, mathMod.scl);
	}
	
}
