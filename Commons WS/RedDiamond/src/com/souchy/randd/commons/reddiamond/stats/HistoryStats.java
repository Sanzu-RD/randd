package com.souchy.randd.commons.reddiamond.stats;

import java.util.Map;

import com.souchy.randd.commons.reddiamond.instances.Action;
import com.souchy.randd.commons.reddiamond.instances.Entity;
import com.souchy.randd.commons.reddiamond.instances.Fight;
import com.souchy.randd.commons.reddiamond.instances.Spell;

public class HistoryStats {

	private static Map<Integer, Fight> fights; // all fights' histories
	
	public static Fight getFight(int fightid) {
		return fights.get(fightid);
	}
	public static void addFight(Fight fight) {
		fights.put(fight.id, fight);
	}
	
	
	public Map<Integer, Action> allActions;
	public Map<Integer, Spell> allSpellsCast; // all spells with the stats they had at the time of casting ? (including creature stats and buffs)
	public Map<Integer, Entity> entities; // all entities by unique id
	
}
