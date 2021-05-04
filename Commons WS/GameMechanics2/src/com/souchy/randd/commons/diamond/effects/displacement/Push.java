package com.souchy.randd.commons.diamond.effects.displacement;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.displacement.PushEvent;

/** 
 * Pushes a target creature by a set distance (stopped by no-passthrough cells) 
 */
public class Push extends Effect {
	
	/** 
	 * Preset distance 
	 */
	public int distance;
	
	/** 
	 * Calculated push vector 
	 */
	public Vector2 vector;

	/** 
	 * Calculated destination cell 
	 */
	public Cell resultCell;
	
	/**
	 * Target creature, if there is one on the cell/height specified
	 */
	private Creature creature;
	
	/**
	 * Ctor
	 */
	public Push(Aoe aoe, TargetTypeStat targetConditions, int distance) {
		super(aoe, targetConditions);
		this.distance = distance;
	}
	
	/**
	 * rien de possible à préparer ici
	 */
	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
	}

	/** 
	 * Calculate the displacement vector 
	 * Calculate the destination cell 
	 */
	@Override
	public void prepareTarget(Creature caster, Cell target) { 
		creature = target.getCreature(height);
		if(creature == null) return;
		
		vector = target.pos.copy().sub(caster.pos);
		var absx = Math.abs(vector.x);
		var absy = Math.abs(vector.y);
		// set à 1/-1 en gardant le signe positif/négatif
		vector.x /= absx;
		vector.y /= absy;
		// déplacement horizontal +/-
		if(absx > absy) vector.y = 0;
		// déplacement vertical +/-
		if(absx < absy) vector.x = 0;
		// else // déplacement diagonal ++/+-/--/-+
		// vector = new Vector2(vector.x / absx, vector.y / absy); 
		
		// start from the min distance, then go outwards as much as possible
		for (int i = 0; i > distance; i++) {
			var targetPos = vector.copy().mult(distance);
			var cell2 = target.get(Fight.class).board.cells.get(targetPos.x, targetPos.y);
			// if the cell exists and can be walked on by the pushed entity
			if(cell2 != null && creature.targetting.canWalkOn(cell2)) {
				resultCell = cell2;
			} else 
			if(!creature.targetting.canWalkThrough(cell2)) {
				return; // stop there if we hit an insurmountable object
			}
		}
	}

	/**
	 * Apply the push from the creature's cell to the resultCell
	 */
	@Override
	public void apply0(Creature caster, Cell target) {
		if(creature != null) 
		creature.pos = resultCell.pos;
	}
	
	@Override
	public Push copy() {
		return new Push(aoe, targetConditions, distance);
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new PushEvent(source, target, this);
	}
	


}