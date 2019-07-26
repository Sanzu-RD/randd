package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;

/** Pulls a target creature by a set distancee */
public class Pull extends Effect<Creature> {
	public int distance;
	@Override
	public void apply(Entity source, Creature target) {
		// calculate direction from the 2 entities, then pull vector * distance
	}
}