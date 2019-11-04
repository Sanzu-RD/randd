package gamemechanics.data.status;

import data.new1.timed.Status;
import gamemechanics.models.Effect.StatsEffect;
import gamemechanics.statics.stats.StatModifier.BasicMod;
import gamemechanics.statics.stats.StatModifier.mo;
import gamemechanics.statics.stats.StatModifier.st;

/**
 * Doubles the cost of walking
 */
public class Heavy extends Status {
	public Heavy() {
		this.effects.add(new StatsEffect(new BasicMod(st.move, mo.costMore, 1)));
	}
	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}
}
