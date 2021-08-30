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
import com.souchy.randd.commons.tealwaters.logging.Log;
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
		Log.format("AddTerrainEffect %s ctor",  this.hashCode());
	}
	
	@Override
	public void prepareCaster(Creature caster, Cell target) {
		var f = caster.get(Fight.class);
		this.terrain = statusBuilder.apply(f);
		this.terrain.parentEffectId = this.id;
		this.terrain.sourceEntityId = caster.id; 

		this.terrain.add(target);
		this.terrain.add(target.pos);
		this.terrain.add(aoe);
		
		// register status to engine and status bus
		terrain.register(f);
		f.statusbus.register(terrain);
		
		Log.format("AddTerrainEffect %s prepareCaster %s %s",  this.hashCode(), terrain, caster.id);
	}
	
	@Override
	public void prepareTarget(Creature caster, Cell target) {
		try {
			terrain.targetEntityId = target.id;
			Log.format("AddTerrainEffect %s prepareTarget %s", this.hashCode(), target.id);
		} catch (Exception e) {
			Log.error("", e);
		}
	}
	
	@Override
	public void apply0(Creature source, Cell cell) {
		//Log.format("AddTerrainEffect %s apply0 %s, %s", this.hashCode(), source, cell);
		var target = cell;
		if(target == null) {
			Log.format("AddTerrainEffect %s : cell == null -> terrain dispose", this.hashCode());
			terrain.dispose();
			return;
		}
		Log.format("AddTerrainEffect %s : addStatus to cell %s", this.hashCode(), target.pos);
		try {
			terrain.aoe.add(target);
			target.statuses.addStatus(terrain);
			
			var crea = target.getCreature(this.height);
			if(crea != null) crea.statuses.addStatus(terrain);
		}catch(Exception e) {
			Log.error("", e);
		}
	}
	
	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new AddTerrainEvent(source, target, this);
	}
	
	@Override
	public AddTerrainEffect copy() {
		var eff = new AddTerrainEffect(aoe, targetConditions, statusBuilder);
		eff.terrain = terrain; // je pense qu'on veut utiliser la même instance du status pour toutes les cellules ? sinon on fera le statusBuilder.apply dans prepareTarget
		return eff;
	}

	
}
