package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Creature;
import gamemechanics.models.entities.Entity;

/** 
 * Pulls a target creature by a set distancee 
 */
public class Pull extends Effect {
	
	public Pull(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
		// TODO Auto-generated constructor stub
	}
	
	public int distance;
	@Override
	public void apply(Entity source, Cell target) {
		// get Creature from cell target, if null do nothing
		// calculate direction from the 2 entities, then pull vector * distance
	}
}