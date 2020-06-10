package com.souchy.randd.data.s1.creatures;

import data.new1.spellstats.CreatureStats;
import gamemechanics.models.CreatureModel;
import gamemechanics.models.Fight;

public class Mesmer extends CreatureModel {

	@Override
	public int id() {
		return 1;
	}

	@Override
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
		return stats;
	}
	
}
