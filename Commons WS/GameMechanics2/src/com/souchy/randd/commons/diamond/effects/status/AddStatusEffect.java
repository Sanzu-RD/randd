package com.souchy.randd.commons.diamond.effects.status;

import java.util.function.Function;

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
	
	/** 
	 * Status supplier to create a new Status instance on each Effect.apply() and apply0
	 * 
	 */
	public Function<Fight, Status> statusBuilder;
	
	/**
	 * New status created for each copy of the effect on each target cell in Effect.apply/apply0
	 */
	public Status status;
	
	public AddStatusEffect(Aoe aoe, TargetTypeStat targetConditions, Function<Fight, Status> statusBuilder) {
		super(aoe, targetConditions);
		this.statusBuilder = statusBuilder;
		this.status = statusBuilder.apply(null);
		this.status.parentEffectId = this.id;
	}
	
	
	@Override
	public void apply0(Creature source, Cell cell) {
//		var f = source.get(Fight.class);
		var target = cell.getCreature(height);
		if(target == null) {
			status.dispose();
			return;
		}
		
		// create status instance
//		var status = statusBuilder.apply(null);
		status.sourceEntityId = source.id; //.ref();
		status.targetEntityId = target.id; //.ref();
		
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
//			f.statusbus.register(status);
//			target.getCreatures().get(0).handlers.register(status);
//		}
		
	}
	
	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new AddStatusEvent(source, target, this);
	}
	
	@Override
	public AddStatusEffect copy() {
		return new AddStatusEffect(aoe, targetConditions, statusBuilder);
	}

}
