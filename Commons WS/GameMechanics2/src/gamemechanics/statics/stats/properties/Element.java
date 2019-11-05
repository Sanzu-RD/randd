package gamemechanics.statics.stats.properties;

public enum Element implements StatProperty {
	
	globalEle, red, green, blue, yellow, white, black
	;
	
	private Element() {
		StatPropertyID.register(this);
	}
}