package com.souchy.randd.commons.diamond.statics.stats.properties.spells;

import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty;

/** 
 * which kind of target the spell/caster can hit <br>
 * Activated bits mean they CAN be targeted <br> 
 * Empty means an empty cell while full means a cell with a creature on it
*/
public enum TargetType { //implements StatProperty {
	
	// if you only want summons, then you deactivate summoners
	// if you only want summoners, then you deactivate summons
	needsLineOfSight, 
	empty, full, 
	allies, enemies, 
	summoners, summons, 
	self // si on peut targetter soi-même
	;
	
//	private TargetingProperty() {
//		StatPropertyID.register(this);
//	}
	
	public int bit() {
		return 1 << ordinal();
//		return (int) Math.pow(ordinal(), 2);
	}
	
	public TargetTypeStat asStat() {
		return new TargetTypeStat(bit());
	}
	
}