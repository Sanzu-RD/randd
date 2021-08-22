package com.souchy.randd.commons.diamond.effects.status;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.status.RemoveTerrainEvent;
import com.souchy.randd.commons.diamond.statusevents.status.RemoveStatusEvent;

public class RemoveTerrainEffect extends Effect {

	// pourrait avoir des int pour duration/stacks?
	private Status status; //Class<? extends Status> c;
	
	public RemoveTerrainEffect(Aoe aoe, TargetTypeStat targetConditions, Status status) {
		super(aoe, targetConditions);
		this.status = status;
	}

	@Override
	public RemoveTerrainEvent createAssociatedEvent(Creature source, Cell target) {
		return new RemoveTerrainEvent(source, target, this);
	}

	@Override
	public void apply0(Creature source, Cell cell) {
		var target = cell;
		
		// Remove status from list
		target.statuses.removeStatus(status); 
	}
	
	@Override
	public RemoveTerrainEffect copy() {
		return new RemoveTerrainEffect(aoe, targetConditions, status);
	}

}
