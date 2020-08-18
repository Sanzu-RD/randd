package com.souchy.randd.commons.diamond.models;


import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.ecs.Entity;
import com.souchy.randd.commons.diamond.models.stats.TargetConditionStat;
import com.souchy.randd.commons.diamond.statusevents.Event;

/**
 * 
 * Parents all effects like DamageEffect, PushEffect, etc
 * 
 * create a new instance of them for each spell ex : effects.add(new PushEffect(aoe, conds))
 * 
 * @author Blank
 *
 */
public abstract class Effect extends Entity {

	public int id;
	public int modelid; // ? not sure
	
	/** 
	 * aoe 
	 */
	public Aoe aoe; 
	/** 
	 * conditions to apply the effect to a target in the aoe 
	 */
	public TargetConditionStat targetConditions;

	public Effect(Fight f, Aoe aoe, TargetConditionStat targetConditions) {
		super(f);
		this.aoe = aoe;
		this.targetConditions = targetConditions;
	}
	
	/**
	 * Create an event associated with this effect for each application of it
	 */
	public abstract Event createAssociatedEvent(Creature source, Cell target);

	
	/**
	 * apply the effect to the aoe around the target cell
	 * @param source
	 * @param cellTarget
	 */
	public void apply(Creature source, Cell cellTarget) { // , Effect parent);
		var board = cellTarget.get(Fight.class).board;

		// level 0 handlers 
		var casterEvent = createAssociatedEvent(source, cellTarget);
		interceptors(source, casterEvent); 
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

			// apply secondary effect
			secondaryEffect(event);
		});
		
		// level 0 reactor has access to all the copies of events & effects made in the Aoe
		reactors(source, casterEvent); 
	}

	/**
	 * Apply a copy of an effect event directly to a target without going through caster handlers. <br>
	 * This can be used by handlers to apply other secondary/tertiary/.. effects. <br>
	 * USE EFFECT.APPLY if you want to create a NEW effect.
	 * @param event - A copy of the parent event with a new target : 
	 * <pre> var e = event.copy(); 
	 * e.target = target; 
	 * Effect.secondaryeffect(e);
	 *  </pre>
	 */
	public static void secondaryEffect(Event event) {
		// level 1 handlers
		interceptors(event.target, event); 	
		modifiers(event.target, event);
		if(event.intercepted) return;
		
		event.effect.prepareTarget(event.source, event.target);
		
		// apply
		event.effect.apply0(event.source, event.target);

		// level 1 handlers
		reactors(event.target, event); 
		reactors(event.source, event);
	}
	
	private static void interceptors(Entity e, Event tempEvent) {
		if(tempEvent.intercepted) return;
		e.get(Fight.class).handlers.interceptors.post(tempEvent);
	}
	private static void modifiers(Entity e, Event tempEvent) {
		if(tempEvent.intercepted) return;
		e.get(Fight.class).handlers.modifiers.post(tempEvent);
	}
	private static void reactors(Entity e, Event tempEvent) {
		if(tempEvent.intercepted) return;
		e.get(Fight.class).handlers.reactors.post(tempEvent);
	}
	
	/**
	 * Pre-calculation for the caster. Cell target is the origin of the aoe
	 */
	public abstract void prepareCaster(Creature caster, Cell aoeOrigin);
	/**
	 * Pre-calculation for the target. Cell target is any cell in the aoe.
	 */
	public abstract void prepareTarget(Creature caster, Cell target);
	/**
	 * Apply the effect to a single target cell (this is called for each cell in the aoe)
	 */
	public abstract void apply0(Creature caster, Cell target);
	/**
	 * Copy an effect so it can be modified independantly for each target
	 */
	public abstract Effect copy();
	
	
	
	
}
