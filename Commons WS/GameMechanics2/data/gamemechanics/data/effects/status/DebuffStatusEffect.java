package gamemechanics.data.effects.status;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class DebuffStatusEffect extends Effect<Entity> {
	@Override
	public void apply(Entity source, Entity target) {
		// TODO target.getStatus().forEach(s -> s.duration--);
		// proc event listeners (recalculate stats OnEffectAction, etc)
	}
}