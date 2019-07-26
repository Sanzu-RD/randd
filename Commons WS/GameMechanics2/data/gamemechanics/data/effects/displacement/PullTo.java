package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Pull the first creature in line to the target cell (stopped by no-passthrough cells) */
public class PullTo extends Effect<Cell> {
	@Override
	public void apply(Entity source, Cell target) {
		// from the source to the target, iterate cells and find the first creature.
		// then try to pull the creature to the target cell until there is an object blocking the way
	}
}