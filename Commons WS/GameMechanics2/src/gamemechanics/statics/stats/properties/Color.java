package gamemechanics.statics.stats.properties;

import gamemechanics.statics.stats.properties.StatProperty.StatPropertyID;

public enum Color implements StatProperty {
	globalEle, red, green, blue, yellow, white, black;
	private Color() {
		StatPropertyID.register(this);
	}
}