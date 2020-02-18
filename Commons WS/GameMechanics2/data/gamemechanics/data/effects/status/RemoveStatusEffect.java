package gamemechanics.data.effects.status;

import data.new1.Effect;
import data.new1.timed.Status;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.status.OnStatusAdd;
import gamemechanics.events.new1.status.OnStatusLose;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class RemoveStatusEffect extends Effect {
	

	private Status status; //Class<? extends Status> c;
	
//	public RemoveStatusEffect(Class<? extends Status> c) {
//		this.c = c;
//	}

	public RemoveStatusEffect(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
	}
	
	@Override
	public void apply0(Entity caster, Cell target) {
		target.getStatus().remove(status); //.remove(c);
		target.handlers.unregister(status);
	}

	@Override
	public Event createAssociatedEvent() {
		return new OnStatusLose(this);
	}
}