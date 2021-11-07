package com.souchy.randd.commons.diamond.effects;

import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;

/**
 * Multiplies the value of an effect based on context (ex: # ap reduced, # entities hit, % life)
 * 
 * @author Blank
 * @date 7 nov. 2021
 */
public class ValueMultiplier {
	
	public int value = 1;
	public MultiplierType type;
	
	
	public Resource resType;
	public TargetType targetType;
	
	
	public static enum MultiplierType {
		None,
		PerXAdded,
		
		PerMaxX,
		PerCurrentX,
		
		PerPercentMaxX,
		PerPercentCurrentX,
		PerPercentCurrentMaxX,
		
		PerTargetHit,
	}
	
}
