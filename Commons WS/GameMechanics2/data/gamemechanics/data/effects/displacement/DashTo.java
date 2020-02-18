package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Dash to the target cell (stopped by no-passthrough cells) */
public class DashTo extends Effect {
	
	public DashTo(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(Entity source, Cell target) {
		//var dist = source.getCell().distanceSquare(target);
		//new Dash(dist).apply(source, target);
		
	}
}