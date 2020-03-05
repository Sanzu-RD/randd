package data.new1;


import java.util.List;

import data.new1.spellstats.base.IntStat;
import data.new1.spellstats.imp.TargetConditions;
import gamemechanics.common.Aoe;
import gamemechanics.data.effects.damage.Damage;
import gamemechanics.events.new1.Event;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/**
 * 
 * Parents all effects like DamageEffect, PushEffect, etc
 * 
 * create a new instance of them for each spell ex : effects.add(new PushEffect(aoe, conds))
 * 
 * @author Blank
 *
 */
public abstract class Effect {

	/** 
	 * aoe 
	 */
	public Aoe aoe; //int[] areaOfEffect;
	/** 
	 * conditions to apply the effect to a target in the aoe 
	 */
	public TargetConditions targetConditions;
	/** 
	 * event associated with the effect that is created and published on every apply to seek interceptors, modifiers and reactors 
	 */
//	protected Event tempEvent;
	
	/**
	 * Ctor
	 */
	public Effect(Aoe aoe, /* int[] areaOfEffect, */ TargetConditions targetConditions) {
		//this.areaOfEffect = areaOfEffect;
		this.aoe = aoe;
		this.targetConditions = targetConditions;
	}
	
	/**
	 * Create an event associated with this effect for each application of it
	 */
	public abstract Event createAssociatedEvent(Entity source, Cell target);

//	public void applyAfter(Entity source, Cell target) {
//		EffectPipeline p = null;
//		var apply = p.insert(this);
//		if(apply) this.apply(source, target);
//	}
	
	/**
	 * apply the effect to the aoe around the target cell
	 * @param source
	 * @param cellTarget
	 */
	public void apply(Entity source, Cell cellTarget) { // , Effect parent);
		var board = cellTarget.fight.board;

		// handlers
		var casterEvent = createAssociatedEvent(source, cellTarget);
		interceptors(source, casterEvent);  // receive handlers have to check for event level >0 to not cancel entire effects like a outward interceptor
		modifiers(source, casterEvent);
		prepareCaster(source, cellTarget);
		if(casterEvent.intercepted) return;
		
		
		// for all cells in the AOE
		aoe.table.foreach((x, y) -> {
			// if the coordinate isnt activated in the aoe
			if(!aoe.table.get(x, y)) return;
			
			// project pos to board cell
			Cell target = board.cells.get(cellTarget.pos.x - aoe.source.x + x, cellTarget.pos.y - aoe.source.y + y);
			if(target == null) return;
			
			// un event différent par cell touchée (copy l'effet aussi)
			var event = casterEvent.copy(); 
			event.target = target;
			
			// handlers
			interceptors(event.target, event); 	
			modifiers(event.target, event);
			prepareTarget(event.source, event.target);
			if(event.intercepted) return;
			
			// apply
			apply0(event.source, event.target);
			
			// handlers
			reactors(event.source, event);
			reactors(event.target, event);
		});
	}

	/**
	 * 
	 * @param event
	 */
	public static void secondaryEffect(Event event) {
		// handlers
		interceptors(event.target, event); 
		modifiers(event.target, event);
		if(event.intercepted) return;
		
		event.effect.prepareTarget(event.source, event.target);
		
		// apply
		event.effect.apply0(event.source, event.target);
		
		// handlers
		reactors(event.source, event);
		reactors(event.target, event);
	}
	
	private static void interceptors(Entity e, Event tempEvent) {
		if(tempEvent.intercepted) return;
		e.handlers.interceptors.post(tempEvent);
	}
	private static void modifiers(Entity e, Event tempEvent) {
		if(tempEvent.intercepted) return;
		e.handlers.modifiers.post(tempEvent);
	}
	private static void reactors(Entity e, Event tempEvent) {
		if(tempEvent.intercepted) return;
		e.handlers.reactors.post(tempEvent);
	}
	
	/**
	 * Pre-calculation for the caster. Cell target is the origin of the aoe
	 */
	public abstract void prepareCaster(Entity caster, Cell aoeOrigin);
	/**
	 * Pre-calculation for the target. Cell target is any cell in the aoe.
	 */
	public abstract void prepareTarget(Entity caster, Cell target);
	/**
	 * Apply the effect to a single target cell (this is called for each cell in the aoe)
	 */
	public abstract void apply0(Entity caster, Cell target);
	/**
	 * Copy an effect so it can be modified independantly for each target
	 */
	public abstract Effect copy();
	
	
	
	
}
