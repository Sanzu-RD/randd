package com.souchy.randd.data.s1.creaturetypes;

import com.souchy.randd.data.s1.main.Elements;

import data.new1.CreatureTypeModel;

public final class ElementalType extends CreatureTypeModel {
	
//	public static final CreatureTypeModel inst = new ElementalType();
	
	private ElementalType() {
		// resources
//		stats.resources.put(Resource.life, new IntStat(400));
//		stats.resources.put(Resource.mana, new IntStat(400));
//		stats.resources.put(Resource.move, new IntStat(400));
//		stats.resources.put(Resource.special, new IntStat(400));
		
		// affinities
		stats.affinity.get(Elements.fire).inc = 10;
		stats.affinity.get(Elements.water).inc = 10;
		stats.affinity.get(Elements.earth).inc = 10;
		stats.affinity.get(Elements.air).inc = 10;
		
		// res
		stats.resistance.get(Elements.fire).inc = 10;
		stats.resistance.get(Elements.water).inc = 10;
		stats.resistance.get(Elements.earth).inc = 10;
		stats.resistance.get(Elements.air).inc = 10;
	}
}
