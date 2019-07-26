package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Dashes a set distance away from target cell (stopped by no-passthrough cells) */
public class DashFrom extends Effect<Cell> {
	public int distance;
	@Override
	public void apply(Entity source, Cell target) {
		
	}
}
