package gamemechanics.data.effects.status;

import java.util.function.Supplier;

import gamemechanics.models.Effect;
import gamemechanics.models.entities.Entity;
import gamemechanics.status.Status;

/** One-shot effect */
public class ApplyStatusEffect extends Effect<Entity> {
	private Supplier<Status> statusBuilder;
	public ApplyStatusEffect(Supplier<Status> statusBuilder) {
		this.statusBuilder = statusBuilder;
	}
	@Override
	public void apply(Entity source, Entity target) {
		var status = statusBuilder.get();
		status.source = source;
		status.target = target;
		
		target.getStatus().add(status);
		// proc event listeners (recalculate stats OnEffectAction, etc)
	}
}
