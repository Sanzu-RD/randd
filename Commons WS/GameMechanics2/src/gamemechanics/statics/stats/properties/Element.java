package gamemechanics.statics.stats.properties;

import gamemechanics.statics.stats.properties.StatProperty;
import gamemechanics.statics.stats.properties.StatProperty.StatPropertyID;

public enum Element implements StatProperty {
	
	globalEle, red, green, blue, yellow, white, black
	;
	
	private Element() {
		StatPropertyID.register(this);
	}
}