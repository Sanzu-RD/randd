package gamemechanics.data.effects.status;

import java.util.function.Supplier;

import data.new1.Effect;
import data.new1.timed.Status;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.events.new1.status.OnStatusAdd;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class ApplyStatusEffect extends Effect {

	private Supplier<Status> statusBuilder;
	
	public ApplyStatusEffect(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
	}

//	public ApplyStatusEffect(Supplier<Status> statusBuilder) {
//		this.statusBuilder = statusBuilder;
//	}
	
	@Override
	public void apply0(Entity source, Cell target) {
		var status = statusBuilder.get();
		
		status.source = source;
		status.target = target;
		status.parent = this;
		
		target.getStatus().add(status);
		target.handlers.register(status);
	}

	@Override
	public Event createAssociatedEvent() {
		return new OnStatusAdd(statusBuilder.get());
	}
}
