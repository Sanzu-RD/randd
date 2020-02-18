package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Teleport to the target cell */
public class Teleport extends Effect {
	
	public Teleport(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
	}

	@Override
	public void apply(Entity source, Cell target) {
		// teleport to target cell
	}
	
}