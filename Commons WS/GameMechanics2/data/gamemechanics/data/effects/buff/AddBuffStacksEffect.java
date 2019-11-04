package gamemechanics.data.effects.buff;

import data.new1.Effect;
import data.new1.timed.Buff;
import data.new1.timed.Status;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class AddBuffStacksEffect extends Effect {
	private Class<? extends Buff> c;
	
	public AddBuffStacksEffect(int[] areaOfEffect, int targetConditions, Class<? extends Buff> c) {
		super(areaOfEffect, targetConditions);
		this.c = c;
	}
	@Override
	public void apply(Entity source, Entity target) {
		// proc event listeners (recalculate stats OnEffectAction, etc)
	}
}