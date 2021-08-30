package com.souchy.randd.commons.diamond.effects.status;

import java.util.function.Function;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
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
	
	/**
	 * 
	 * @param aoe : AoeBuilders.single.get()
	 * @param targetConditions : TargetType.full.asStat()
	 * @param statusBuilder : <code> (ff) -> {
			var b = new Burning(ff, 0, 0);
			b.duration = 3;
			b.stacks = 1;
			b.canDebuff = true;
			b.canRemove = true;
			return b;
		}</code>
	 */
	public AddStatusEffect(Aoe aoe, TargetTypeStat targetConditions, Function<Fight, Status> statusBuilder) {
		super(aoe, targetConditions);
		this.statusBuilder = statusBuilder;
	}
	
	@Override
	public void prepareTarget(Creature caster, Cell target) {
		// maybe build the status here so it's a different instance for each target
		this.status = statusBuilder.apply(null);
		this.status.parentEffectId = this.id;
	}
	
	@Override
	public void apply0(Creature source, Cell cell) {
		var f = source.get(Fight.class);
		var target = cell.getCreature(height);
		if(target == null) {
			status.dispose();
			return;
		}
		
		// create status instance
//		var status = statusBuilder.apply(null);
		status.sourceEntityId = source.id; //.ref();
		status.targetEntityId = target.id; //.ref();
		
		// set the status pos to the creature's pos so that it can follow the creature (fx)
		status.add(target.pos);
		status.add(target);
		
		
		// Add status. StatusList manages fusion by itsef
		// FIXME add status to creature or cell 
		// FIXME adding a status to a cell should trigger reactors to apply statuses on creatures 
		// FIXME aka all cells should have a reactor status that adds statuses to creatures on them (applicability determined by the status)
		
//		if(status instanceof TerrainEffect) {
//			target.getStatus().addStatus(status);
//			// register in the eventpipeline if the status is either of interceptors/modifiers/reactors
//			target.handlers.register(status);
//		} else {
			var regis = target.statuses.addStatus(status);
			
			if(regis) {
				status.register(f);
				f.statusbus.register(status);
			}
			
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
		var eff = new AddStatusEffect(aoe, targetConditions, statusBuilder);
		//eff.status = this.status; // not sure, maybe it should be a copy for actual statuses, contrarily to terraineffects
		return eff;
	}

}
