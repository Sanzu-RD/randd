package gamemechanics.statics.stats.modifiers;

import gamemechanics.statics.stats.modifiers.Modifier.ModifierID;

public enum eleMod implements Modifier {
	affinity, res, pen,
	dmg, heal,
	summon;
	private eleMod() {
		ModifierID.register(this);
	}
}