package com.souchy.randd.commons.diamond.effects.status;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.status.RemoveStatusEvent;

/** One-shot effect */
public class RemoveStatusEffect extends Effect {

	// pourrait avoir des int pour duration/stacks?
	private Status status; //Class<? extends Status> c;
	
	public RemoveStatusEffect(Fight f, Aoe aoe, TargetTypeStat targetConditions, Status status) {
		super(f, aoe, targetConditions);
		this.status = status;
	}

	@Override
	public RemoveStatusEvent createAssociatedEvent(Creature source, Cell target) {
		return new RemoveStatusEvent(source, target, this);
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
		get(Fight.class).statusbus.unregister(status);
		status.dispose();
	}
	
	@Override
	public RemoveStatusEffect copy() {
		return new RemoveStatusEffect(get(Fight.class), aoe, targetConditions, status);
	}

}