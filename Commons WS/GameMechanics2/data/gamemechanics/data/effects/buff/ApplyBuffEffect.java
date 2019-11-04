package gamemechanics.data.effects.buff;

import java.util.function.Supplier;

import data.new1.timed.Status;
import gamemechanics.models.Effect;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class ApplyBuffEffect extends Effect<Entity> {
	private Supplier<Status> statusBuilder;
	public ApplyBuffEffect(Supplier<Status> statusBuilder) {
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
