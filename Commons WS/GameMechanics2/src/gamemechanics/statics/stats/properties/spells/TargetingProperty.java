package gamemechanics.statics.stats.properties.spells;

import gamemechanics.statics.stats.properties.StatProperty;
import gamemechanics.statics.stats.properties.StatProperty.StatPropertyID;

/** which kind of target the spell/caster can hit (each of them are a bool as to if they can be targeted) (empty means an empty cell) */
public enum TargetingProperty implements StatProperty {
	empty, allies, enemies, summons, summoners, needsLineOfSight;
	private TargetingProperty() {
		StatPropertyID.register(this);
	}
	
}