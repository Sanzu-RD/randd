package com.souchy.randd.commons.reddiamond.instances;

import com.souchy.randd.commons.reddiamond.stats.CreatureStats;

public class Creature extends Entity {
	
	/*
	 * int modelid;
	 * 
	 * Team team;
	 * Vector2 pos;
	 * stats stats;
	 * 
	 * list<> spells;
	 * list<> status;
	 * 
	 * list<> summons;
	 * int parentSummonerId;
	 * 
	 * int carrierId; // ex porté par panda
	 * int carriedId; // ex porte qqn
	 * 
	 * 
	 * getTotalCreatureStats();
	 * getTotalSpellStats(spellid);
	 * 
	 * 
	 */
	
	public CreatureStats stats;
	
	public CreatureStats getTotalCreatureStats() {
		return stats;
	}
	
	
}
