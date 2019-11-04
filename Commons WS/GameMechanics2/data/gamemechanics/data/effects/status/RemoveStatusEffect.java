package gamemechanics.data.effects.status;

import data.new1.timed.Status;
import gamemechanics.models.Effect;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class RemoveStatusEffect extends Effect<Entity> {
	private Class<? extends Status> c;
	public RemoveStatusEffect(Class<? extends Status> c) {
		this.c = c;
	}
	@Override
	public void apply(Entity source, Entity target) {
		target.getStatus().remove(c);
		// proc event listeners (recalculate stats OnEffectAction, etc)
	}
}