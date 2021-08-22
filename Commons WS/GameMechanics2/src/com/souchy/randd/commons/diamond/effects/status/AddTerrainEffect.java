package com.souchy.randd.commons.diamond.effects.status;

import java.util.function.Function;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.status.AddTerrainEvent;
import com.souchy.randd.commons.diamond.statusevents.status.AddStatusEvent;

/**
 * 
 * 
 * @author Blank
 * @date 19 août 2021
 */
public class AddTerrainEffect extends Effect {

	// pourrait avoir des int pour duration/stacks?
	
	/** 
	 * Status supplier to create a new Status instance on each Effect.apply() and apply0
	 * 
	 */
	public Function<Fight, TerrainEffect> statusBuilder;
	
	/**
	 * New status created for each copy of the effect on each target cell in Effect.apply/apply0
	 */
	public TerrainEffect terrain;
	
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
	public AddTerrainEffect(Aoe aoe, TargetTypeStat targetConditions, Function<Fight, TerrainEffect> statusBuilder) {
		super(aoe, targetConditions);
		this.statusBuilder = statusBuilder;
//		this.status = statusBuilder.apply(null);
//		this.status.parentEffectId = this.id;
	}
	
	@Override
	public void prepareCaster(Creature caster, Cell target) {
		this.terrain = statusBuilder.apply(caster.get(Fight.class));
		this.terrain.parentEffectId = this.id;
		
		terrain.sourceEntityId = caster.id; 
	}
	
	@Override
	public void prepareTarget(Creature caster, Cell target) {
		terrain.targetEntityId = target.id; 
	}
	
	@Override
	public void apply0(Creature source, Cell cell) {
		var target = cell;
		if(target == null) {
			terrain.dispose();
			return;
		}
		
		target.statuses.addStatus(terrain);
		
	}
	
	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new AddTerrainEvent(source, target, this);
	}
	
	@Override
	public AddTerrainEffect copy() {
		return new AddTerrainEffect(aoe, targetConditions, statusBuilder);
	}

	
}
