package gamemechanics.data.effects.status;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import data.new1.timed.Status;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.status.OnRemoveStatusEvent;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;

/** One-shot effect */
public class RemoveStatusEffect extends Effect {

	// pourrait avoir des int pour duration/stacks?
	private Status status; //Class<? extends Status> c;
	
	public RemoveStatusEffect(Fight f, Aoe aoe, TargetConditionStat targetConditions, Status status) {
		super(f, aoe, targetConditions);
		this.status = status;
	}

	@Override
	public OnRemoveStatusEvent createAssociatedEvent(Creature source, Cell target) {
		return new OnRemoveStatusEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
		
	}
	
	@Override
	public void prepareTarget(Creature caster, Cell target) {
		
	}
	
	@Override
	public void apply0(Creature source, Cell target) {
		// Remove status 
		// FIXME : apply to cells and creatures and remove the whole aoe if the status is an aoe terrain effect (+ remove from all creatures in it)
		target.statuses.removeStatus(status); //.remove(c);
		// unregister from the pipeline if the status is either of interceptors/modifiers/reactors 
		get(Fight.class).handlers.unregister(status);
	}
	
	@Override
	public RemoveStatusEffect copy() {
		return new RemoveStatusEffect(get(Fight.class), aoe, targetConditions, status);
	}

}