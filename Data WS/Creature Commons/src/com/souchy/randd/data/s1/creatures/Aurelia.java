package com.souchy.randd.data.s1.creatures;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;

/**
 * 
 * 
 * @author Blank
 * @date 4 juill. 2020
 */
public class Aurelia extends CreatureModel {

	@Override
	public int id() {
		return 9;
	}

	@Override
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
		return stats;
	}
	
}
