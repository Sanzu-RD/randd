package com.souchy.randd.data.s1.creatures;

import com.souchy.randd.data.s1.main.Elements;

import data.new1.spellstats.CreatureStats;
import data.new1.spellstats.base.IntStat;
import gamemechanics.models.CreatureModel;
import gamemechanics.statics.stats.properties.Resource;

public class Sungjin extends CreatureModel {

	@Override
	public int id() {
		return 2;
	}

	@Override
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
		stats.resources.put(Resource.life, new IntStat(1500)); //.get(Resource.life).base = 1500;
		stats.resources.put(Resource.mana, new IntStat(12)); 
		stats.resources.put(Resource.move, new IntStat(6)); 

		stats.affinity.get(Elements.ice).inc = 30; //.put(Elements.water, new IntStat(30));
		stats.affinity.get(Elements.dark).inc = 30; //.put(Elements.dark, new IntStat(30));
		return stats;
	}
	
}
