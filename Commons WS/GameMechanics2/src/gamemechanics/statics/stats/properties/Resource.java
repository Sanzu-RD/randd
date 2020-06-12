package gamemechanics.statics.stats.properties;

public enum Resource implements StatProperty {

	life, 
	mana, 
	move, 
	special;
	
	private Resource() {
		StatPropertyID.register(this);
	}
	
}