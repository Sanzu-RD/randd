package gamemechanics.statics.stats.properties;

import gamemechanics.statics.stats.properties.StatProperty.StatPropertyID;

public enum CreatureProperty implements StatProperty {
	visible, maxSummons;
	private CreatureProperty() {
		StatPropertyID.register(this);
	}
}