package com.souchy.randd.data.s1.creatures;

import data.new1.spellstats.CreatureStats;
import gamemechanics.models.CreatureModel;

/**
 * 
 * 
 * @author Blank
 * @date 4 juill. 2020
 */
public class Flora extends CreatureModel {

	@Override
	public int id() {
		return 6;
	}

	@Override
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
		return stats;
	}
	
	
}