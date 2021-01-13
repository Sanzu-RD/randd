package com.souchy.randd.commons.diamond.models;


import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.special.HeightStat;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statics.filters.Height;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;

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
	public TargetTypeStat targetConditions;
	/**
	 * Z / Height filter. The effect will only target creatures of the corresponding heights
	 */
	public HeightStat height = new HeightStat(Height.floor.bit);

	/**
	 * Ctor
	 */
	public Effect(Fight f, Aoe aoe, TargetTypeStat targetConditions) {
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
//		Log.info("Effect Apply, intercepted? " + casterEvent.intercepted);
		if(casterEvent.intercepted) return;

//		Log.info("Effect.apply, height : " + height.value());
		
		// for all cells in the AOE, copy the event and the effect as a child
		aoe.table.foreachTrue((i, j) -> {
			// if the coordinate isnt activated in the aoe
//			if(!aoe.table.get(i, j)) return;

			// project pos to board cell
			double x = aoe.projectX(i, cellTarget.pos.x);
			double y = aoe.projectY(j, cellTarget.pos.y);
			Cell target = board.get(x, y); // cellTarget.pos.x - aoe.source.x + i, cellTarget.pos.y - aoe.source.y + j);
			
			Log.info("Effect Apply, ij [" + i + "," + j + "] cell [" + x + ", " + y + "] = " + target);
			
			if(target == null) return;
			
			// copy event and effect for each accepted height on the cell
			for(Height h : Height.values()) {
				if(!this.height.has(h)) continue;
				Log.info("Effect.apply, for height " + h); // + " has? " + this.height.has(h));
				
				var event = casterEvent.copy(); 
				event.target = target;
				event.effect.height = h.stat();
				
				// apply secondary effect
				secondaryEffect(event);
			}
			
		});
		Log.info("Effect.apply, going to reactors 0");
		
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
		Log.info("Effect.secondaryEffect on level: " + event.level);
		// level 1 handlers
		interceptors(event.target, event); 	
		modifiers(event.target, event);
		if(event.intercepted) return;
		
		event.effect.prepareTarget(event.source, event.target);
		
		// apply
		event.effect.apply0(event.source, event.target);
		
		// level 1 handlers
		Log.info("Effect.secondaryEffect, going to reactors 1");
		reactors(event.target, event); 
//		reactors(event.source, event);
	}
	
	private static void interceptors(Entity e, Event tempEvent) {
		if(tempEvent.intercepted) return;
		//e.get(Fight.class)
		tempEvent.source.get(Fight.class).statusbus.interceptors.post(tempEvent);
	}
	private static void modifiers(Entity e,  Event tempEvent) {
		if(tempEvent.intercepted) return;
		//e.get(Fight.class)
		tempEvent.source.get(Fight.class).statusbus.modifiers.post(tempEvent);
	}
	private static void reactors(Entity e,  Event tempEvent) {
		if(tempEvent.intercepted) return;
		//e.get(Fight.class)
		tempEvent.source.get(Fight.class).statusbus.reactors.post(tempEvent);
	}
	
	/**
	 * Pre-calculation for the caster. Cell target is the origin of the aoe
	 */
	public abstract void prepareCaster(Creature caster, Cell target);
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
	

	
	/**
	 * Gets the first creature on the cell matching the height of the effect, or null 
	 */
	public Creature getCreatureTarget(Cell target) {
		return target.getCreature(height);
	}
	
	
}
