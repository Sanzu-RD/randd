package com.souchy.randd.commons.diamond.effects.status;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.status.AddStatusEvent;

public class AddStatusEffect extends Effect {
	
	// pourrait avoir des int pour duration/stacks?
	
	/** status supplier to create a status instance on effect.apply */
	private Supplier<Status> statusBuilder;
	
	public AddStatusEffect(Fight f, Aoe aoe, TargetTypeStat targetConditions, Supplier<Status> statusBuilder) {
		super(f, aoe, targetConditions);
		this.statusBuilder = statusBuilder;
	}
	
	
	@Override
	public void apply0(Creature source, Cell cell) {
		var target = cell.getCreature(height);
		
		// create status instance
		var status = statusBuilder.get();
		status.register(source.get(Fight.class));
		status.sourceEntityId = source.id; //.ref();
		status.targetEntityId = target.id; //.ref();
		status.parentEffectId = this.id;

		// Add status. StatusList manages fusion by itsef
		// FIXME add status to creature or cell 
		// FIXME adding a status to a cell should trigger reactors to apply statuses on creatures 
		// FIXME aka all cells should have a reactor status that adds statuses to creatures on them (applicability determined by the status)
		
//		if(status instanceof TerrainEffect) {
//			target.getStatus().addStatus(status);
//			// register in the eventpipeline if the status is either of interceptors/modifiers/reactors
//			target.handlers.register(status);
//		} else {
			target.statuses.addStatus(status);
			// register in the eventpipeline if the status is either of interceptors/modifiers/reactors
			source.get(Fight.class).statusbus.register(status);
//			target.getCreatures().get(0).handlers.register(status);
//		}
		
	}
	
	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new AddStatusEvent(source, target, this);
	}
	
	@Override
	public AddStatusEffect copy() {
		return new AddStatusEffect(get(Fight.class), aoe, targetConditions, statusBuilder);
	}

}
