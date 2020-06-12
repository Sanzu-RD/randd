package gamemechanics.statics.stats.modifiers;

public enum mathMod implements Modifier {
	flat, scl, more, bool;
	private mathMod() {
		ModifierID.register(this);
	}
}