package gamemechanics.statics.stats.modifiers;

import gamemechanics.statics.stats.modifiers.Modifier.ModifierID;

public enum mathMod implements Modifier {
	flat, scl, more, bool;
	private mathMod() {
		ModifierID.register(this);
	}
}