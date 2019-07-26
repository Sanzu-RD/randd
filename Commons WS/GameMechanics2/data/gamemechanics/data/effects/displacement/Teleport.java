package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Teleport to the target cell */
public class Teleport extends Effect<Cell> {
	@Override
	public void apply(Entity source, Cell target) {
		// teleport to target cell
	}
}