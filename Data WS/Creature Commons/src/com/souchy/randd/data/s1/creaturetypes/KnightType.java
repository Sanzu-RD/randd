package com.souchy.randd.data.s1.creaturetypes;

import com.souchy.randd.data.s1.main.Elements;

import data.new1.CreatureTypeModel;
import data.new1.spellstats.base.IntStat;
import gamemechanics.statics.stats.properties.Resource;

public final class KnightType extends CreatureTypeModel {
	
	public static final CreatureTypeModel inst = new KnightType();
	
	private KnightType() {
		// resources
		stats.resources.put(Resource.life, new IntStat(300));
		
		// affinities
		stats.affinity.get(Elements.steel).inc = 10;
		
		// res
		stats.resistance.get(Elements.steel).inc = 10;
		stats.resistance.get(Elements.physical).inc = 10;
		
		
		// passive is  global flat res
		stats.resistance.get(Elements.global).baseflat = 20;
	}
	
}
