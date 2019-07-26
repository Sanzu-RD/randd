package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Pushes the first creature in line with the source to the target cell (stopped by no-passthrough cells) */
public class PushTo extends Effect<Cell> {
	@Override
	public void apply(Entity source, Cell target) {
		// from the source to the target, iterate cells and find the first creature.
		// then try to push the creature to the target cell until there is an object blocking the way
	}
}