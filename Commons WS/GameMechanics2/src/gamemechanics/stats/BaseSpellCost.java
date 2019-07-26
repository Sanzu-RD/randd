package gamemechanics.stats;

import static gamemechanics.stats.StatModifier.mo.fight;

import gamemechanics.stats.StatModifier.BasicMod;

/**
 * Basic spell cost. Spells use this.
 * To then increase the cost of a spell via a mod, use BasicMod('resource', costFlat/costScl/costMore, value);
 */
public class BaseSpellCost extends BasicMod {
	
	public BaseSpellCost(st s, double v) { 
		super(s, fight, v); 
	}
	
}