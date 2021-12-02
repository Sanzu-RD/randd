package com.souchy.randd.commons.reddiamond.common;

import com.souchy.randd.commons.reddiamond.enums.ContextScope;
import com.souchy.randd.commons.reddiamond.enums.Resource;
import com.souchy.randd.commons.reddiamond.enums.TargetType;
import com.souchy.randd.commons.reddiamond.enums.ValueMultiplierType;
import com.souchy.randd.commons.reddiamond.instances.Effect;
import com.souchy.randd.commons.reddiamond.instances.Fight;
import com.souchy.randd.commons.reddiamond.stats.ContextStats;
import com.souchy.randd.commons.reddiamond.stats.HistoryStats;

/**
 * Multiplies the value of an effect based on context (ex: # ap reduced, # entities hit, % life)
 * 
 * @author Blank
 * @date 7 nov. 2021
 */
public class ValueMultiplier {
	/**
	 * multiplier
	 */
	public int mult = 1;
	public ValueMultiplierType type;
	public ContextScope scope;
	
	
	public Resource resType;
	public TargetType targetType;
	
	
	/**
	 * 
	 * @param context - Current context of the caller (most likely an effect trying to get its multiplier)
	 * @return
	 */
	public int get(ContextStats context) {
		Fight f = HistoryStats.getFight(context.getFightId());
		switch(scope) {
			case Fight:
				break;
			case Round:
				break;
			case Turn:
				break;
			case Action:
				break;
			case Effect:
				Effect e = (Effect) f.history.entities.get(context.getEffectId());
				e.context.resourceAdded.get(e);
				break;
			default:
				break;
		}
		
		return mult;
	}
	
	
	
	
	
	
}
