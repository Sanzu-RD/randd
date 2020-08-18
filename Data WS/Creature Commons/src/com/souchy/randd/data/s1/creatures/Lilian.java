package com.souchy.randd.data.s1.creatures;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;

/**
 * 
 * 
 * @author Blank
 * @date 4 juill. 2020
 */
public class Lilian extends CreatureModel {

	@Override
	public int id() {
		return 39;
	}

	@Override
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
		return stats;
	}
	
	
}
