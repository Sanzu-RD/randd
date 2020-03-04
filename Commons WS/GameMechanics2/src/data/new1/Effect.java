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
		interceptors(source, casterEvent);         	// FIXME problème ici est de différencier les interceptors de type source et target. 
												  	// FIXME Ex : si le caster est sacrifié, ça cancellerait l'effet au complet sur toutes les cibles
													//    pcq actuellement y'a aucune différence entre les deux si le caster lance un sort de zone et se tape luimeme
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
			
			// FIXME mettre caster et target dans Event pour pouvoir chnager le target d'un event et l'utiliser ici v
			
			// handlers
			interceptors(target, event); 	// FIXME il y a des problèmes avec la copy et application d'un effet (boucles infini) 
											// FIXME (Ex : handler de partage de dégâts problématique surtout) 
											// FIXME Le nouvel effet doit proc les handlers et apply comme il faut, mais doit pas reproc le partage à nouveau
			modifiers(target, event);
			prepareTarget(source, target);
			if(event.intercepted) return;
			
			// apply
			apply0(source, target);
			
			// handlers
			reactors(source, event);
			reactors(target, event);
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
	 * Pre-calculation for the caster
	 */
	public abstract void prepareCaster(Entity caster, Cell target);
	/**
	 * Pre-calculation for the target
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
