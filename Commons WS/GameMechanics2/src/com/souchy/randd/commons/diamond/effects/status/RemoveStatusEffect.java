package com.souchy.randd.commons.diamond.effects.status;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.status.RemoveStatusEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;

/** One-shot effect */
public class RemoveStatusEffect extends Effect {

	// pourrait avoir des int pour duration/stacks?
	private Status status; //Class<? extends Status> c;
	
	public RemoveStatusEffect(Aoe aoe, TargetTypeStat targetConditions, Status status) {
		super(aoe, targetConditions);
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
	public void apply0(Creature source, Cell cell) {
		// FIXME : apply to cells and creatures and remove the whole aoe if the status is an aoe terrain effect (+ remove from all creatures in it)
		
		var target = cell.getCreature(this.height);
		if(target == null) {
			Log.error("RemoveStatusEffect target null %s %s %s", this.height, cell.pos, this);
			return;
		}
		// Remove status from list
		var removed = target.statuses.removeStatus(status); //.remove(c);

		// unregister status from engine, systems and status bus
		if(removed) status.dispose();
		
		// unregister status bus
//		status.get(Fight.class).statusbus.unregister(status);
		// unregister in fight
//		status.dispose();
	}
	
	@Override
	public RemoveStatusEffect copy() {
		return new RemoveStatusEffect(aoe, targetConditions, status);
	}

}