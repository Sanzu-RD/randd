package gamemechanics.statics.stats.properties.spells;

import gamemechanics.statics.stats.properties.StatProperty;

/** 
 * which kind of target the spell/caster can hit <br>
 * Activated bits mean they CAN be targeted <br> 
 * Empty means an empty cell while full means a cell with a creature on it
*/
public enum TargetingProperty implements StatProperty {
	
	needsLineOfSight, empty, full, allies, enemies, summons, summoners;
	
	private TargetingProperty() {
		StatPropertyID.register(this);
	}
	
}