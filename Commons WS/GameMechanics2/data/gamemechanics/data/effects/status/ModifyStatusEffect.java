package gamemechanics.data.effects.status;

import data.new1.Effect;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class ModifyStatusEffect extends Effect {
	
	public int modStacks, modDuration;
	
	public ModifyStatusEffect(int[] areaOfEffect, int targetConditions, int modStacks, int modDuration) {
		super(areaOfEffect, targetConditions);
		this.modStacks = modStacks;
		this.modDuration = modDuration;
	}

	
	@Override
	public void apply(Entity caster, Cell target) {
		
		
	}
}