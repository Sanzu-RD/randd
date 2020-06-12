package gamemechanics.statics.stats.modifiers;

public enum eleMod implements Modifier {
	affinity, res, pen,
	dmg, heal,
	summon;
	private eleMod() {
		ModifierID.register(this);
	}
}