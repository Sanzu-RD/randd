package com.souchy.randd.data.s1.creatures;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.data.s1.main.Elements;

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

		stats.affinity.get(Elements.water).inc = 30; //.put(Elements.water, new IntStat(30));
		stats.affinity.get(Elements.dark).inc = 30; //.put(Elements.dark, new IntStat(30));
		return stats;
	}
	
}
