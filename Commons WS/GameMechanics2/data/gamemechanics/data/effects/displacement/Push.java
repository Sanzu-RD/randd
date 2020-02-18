package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;

/** Pushes a target creature by a set distance (stopped by no-passthrough cells) */
public class Push extends Effect {

	public int distance;
	
	public Push(int[] areaOfEffect, int targetConditions, int distance) {
		super(areaOfEffect, targetConditions);
		this.distance = distance;
	}

	@Override
	public void apply(Entity caster, Cell target) {
		// calculate direction from the 2 entities, then push vector * distance
	}

}