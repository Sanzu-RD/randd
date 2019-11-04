package gamemechanics.data.effects.buff;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class AddBuffDurationEffect extends Effect {
	
	public AddBuffDurationEffect(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
	}

	@Override
	public void apply(Entity source, Cell target) {
		// TODO target.getStatus().forEach(s -> s.duration--);
		// proc event listeners (recalculate stats OnEffectAction, etc)
	}
	
}