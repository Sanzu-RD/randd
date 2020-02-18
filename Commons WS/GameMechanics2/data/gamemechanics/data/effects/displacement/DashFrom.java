package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;

/** Dashes a set distance away from target cell (stopped by no-passthrough cells) */
public class DashFrom extends Effect {

	public int distance;
	
	public DashFrom(int[] areaOfEffect, int targetConditions, int distance) {
		super(areaOfEffect, targetConditions);
		this.distance = distance;
	}
	
	@Override
	public void apply(Entity source, Cell target) {
		var p1 = source.getCell().pos;
		var p2 = target.pos;
		
		//var d = p2.copy().sub(p1).abs(); // delta en absolu
	}
}
