package com.souchy.randd.commons.diamond.statics.stats.properties.creatures;

import gamemechanics.statics.stats.properties.StatProperty;
import gamemechanics.statics.stats.properties.StatProperty.StatPropertyID;

public enum CreatureProperty implements StatProperty {
	
	visible, maxSummons, range
	;
	
	private CreatureProperty() {
		StatPropertyID.register(this);
	}
}