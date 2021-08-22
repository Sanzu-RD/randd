package com.souchy.randd.data.s1.creatures.real;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;

public class Real extends CreatureModel {

	public static final int modelid = 13;

	@Override
	public int id() {
		return modelid;
	}

	@Override
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
		return stats;
	}
	
}
