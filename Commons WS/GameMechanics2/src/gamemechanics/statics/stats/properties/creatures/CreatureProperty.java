package gamemechanics.statics.stats.properties.creatures;

import gamemechanics.statics.stats.properties.StatProperty;

public enum CreatureProperty implements StatProperty {
	
	visible, maxSummons, range
	;
	
	private CreatureProperty() {
		StatPropertyID.register(this);
	}
}