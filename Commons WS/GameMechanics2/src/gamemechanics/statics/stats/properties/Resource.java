package gamemechanics.statics.stats.properties;

import gamemechanics.statics.stats.properties.StatProperty.StatPropertyID;

public enum Resource implements StatProperty {

	life, 
	mana, 
	move, 
	special;
	
	private Resource() {
		StatPropertyID.register(this);
	}
	
}