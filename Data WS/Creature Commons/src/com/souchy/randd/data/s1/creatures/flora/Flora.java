package com.souchy.randd.data.s1.creatures.flora;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;

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
