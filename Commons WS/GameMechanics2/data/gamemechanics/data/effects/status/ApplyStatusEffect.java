package gamemechanics.data.effects.status;

import java.util.function.Supplier;

import data.new1.timed.Status;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class ApplyStatusEffect extends data.new1.Effect {
	
	public ApplyStatusEffect(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
		// TODO Auto-generated constructor stub
	}

	private Supplier<Status> statusBuilder;
//	public ApplyStatusEffect(Supplier<Status> statusBuilder) {
//		this.statusBuilder = statusBuilder;
//	}
	
	@Override
	public void apply(Entity source, Cell target) {
		var status = statusBuilder.get();
		status.source = source;
		status.target = target;
		
		target.getStatus().add(status);
		// proc event listeners (recalculate stats OnEffectAction, etc)
	}
}
