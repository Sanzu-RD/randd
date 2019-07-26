package gamemechanics.data.effects.displacement;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Dashes a set distance in the direction of the target cell (stopped by no-passthrough cells) */
public class DashDist extends Effect<Cell> {
	public int distance;
	public DashDist(int dist) {
		this.distance = dist;
	}
	@Override
	public void apply(Entity source, Cell target) {
		var p1 = source.getCell().pos;
		var p2 = target.pos;
		
		var d = p2.copy().sub(p1).abs(); // delta en absolu
		
		
		
	}
}
