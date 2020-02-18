package data.new1;


import java.util.List;

import gamemechanics.common.Aoe;
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
	public int targetConditions;
	/** 
	 * event associated with the effect that is created and published on every apply to seek interceptors, modifiers and reactors 
	 */
	protected Event tempEvent;
	
	/**
	 * Ctor
	 */
	public Effect(Aoe aoe, /* int[] areaOfEffect, */ int targetConditions) {
		//this.areaOfEffect = areaOfEffect;
		this.aoe = aoe;
		this.targetConditions = targetConditions;
	}
	
	/**
	 * Create an event associated with this effect for each application of it
	 */
	public abstract Event createAssociatedEvent();

//	public void applyAfter(Entity source, Cell target) {
//		EffectPipeline p = null;
//		var apply = p.insert(this);
//		if(apply) this.apply(source, target);
//	}
	
	public void apply(Entity caster, Cell target) { // , Effect parent);
		var board = target.fight.board;
		
		// for all cells in the AOE
		aoe.table.foreach((x, y) -> {
			// if the coordinate isnt included
			if(!aoe.table.get(x, y)) return;
			
			// project pos to board cell
			Cell t = board.cells.get(target.pos.x - aoe.source.x + x, target.pos.y - aoe.source.y + y);
			
			// un event différent par cell touchée
			tempEvent = createAssociatedEvent();
			
			// proc interceptors and modifiers
			procBefore(caster);
			procBefore(t);
			if(tempEvent.intercepted) return;
			
			// apply
			apply0(caster, t);
			
			// proc reactors
			procAfter(caster);
			procAfter(t);
		});
		

	}

	public abstract void apply0(Entity caster, Cell target);
	
	public void procBefore(Entity e) {
		if(tempEvent.intercepted) return;
		e.handlers.interceptors.post(e);
		if(tempEvent.intercepted) return;
		e.handlers.modifiers.post(e);
	}
	
	public void procAfter(Entity e) {
		e.handlers.reactors.post(e);
	}
	
	
}
