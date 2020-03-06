package gamemechanics.data.effects.status;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditions;
import data.new1.timed.Status;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.status.OnRemoveStatusEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class RemoveStatusEffect extends Effect {

	// pourrait avoir des int pour duration/stacks?
	private Status status; //Class<? extends Status> c;
	
	public RemoveStatusEffect(Aoe aoe, TargetConditions targetConditions, Status status) {
		super(aoe, targetConditions);
		this.status = status;
	}

	@Override
	public OnRemoveStatusEvent createAssociatedEvent(Entity source, Cell target) {
		return new OnRemoveStatusEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Entity caster, Cell aoeOrigin) {
		
	}
	
	@Override
	public void prepareTarget(Entity caster, Cell target) {
		
	}
	
	@Override
	public void apply0(Entity source, Cell target) {
		// Remove status 
		// FIXME : apply to cells and creatures and remove the whole aoe if the status is an aoe terrain effect (+ remove from all creatures in it)
		target.getStatus().remove(status); //.remove(c);
		// unregister from the pipeline if the status is either of interceptors/modifiers/reactors 
		target.handlers.unregister(status);
	}
	
	@Override
	public RemoveStatusEffect copy() {
		return new RemoveStatusEffect(aoe, targetConditions, status);
	}

}