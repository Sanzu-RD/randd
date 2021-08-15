package com.souchy.randd.data.s1.creatures;

import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;


/**
 * 
 * 
 * @author Blank
 * @date 9 août 2021
 */
public class Fantasma extends CreatureModel {

	@Override
	public int id() {
		return 10;
	}

	@Override
	protected CreatureStats initBaseStats() {
		var stats = new CreatureStats();
		return stats;
	}
	
}