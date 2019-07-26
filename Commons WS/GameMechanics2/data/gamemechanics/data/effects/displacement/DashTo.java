package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Dash to the target cell (stopped by no-passthrough cells) */
public class DashTo extends Effect<Cell> {
	@Override
	public void apply(Entity source, Cell target) {
		//var dist = source.getCell().distanceSquare(target);
		//new Dash(dist).apply(source, target);
		
		
	}
}