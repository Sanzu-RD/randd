package gamemechanics.statics.stats.properties;

import gamemechanics.statics.stats.properties.StatProperty.StatPropertyID;

public enum SpellProperty implements StatProperty {
	isInstant, cooldown, maxCastsPerTurn, maxCastsPerTurnPerTarget, minRange, maxRange, minRangePattern, maxRangePattern; //inLine(19), inDiagonal(20);
	private SpellProperty() {
		StatPropertyID.register(this);
	}
}