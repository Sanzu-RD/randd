package gamemechanics.data.status;

import gamemechanics.models.Effect.StatsEffect;
import gamemechanics.stats.StatModifier.BasicMod;
import gamemechanics.stats.StatModifier.mo;
import gamemechanics.stats.StatModifier.st;
import gamemechanics.status.Status;

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
