package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;

/** Pushes a target creature by a set distance (stopped by no-passthrough cells) */
public class Push extends Effect<Creature> {
	public int distance;
	@Override
	public void apply(Entity source, Creature target) {
		// calculate direction from the 2 entities, then push vector * distance
	}
}