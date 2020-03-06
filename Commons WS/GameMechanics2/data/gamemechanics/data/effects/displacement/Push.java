package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditions;
import gamemechanics.common.Aoe;
import gamemechanics.common.generic.Vector2;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.displacement.OnPushEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Pushes a target creature by a set distance (stopped by no-passthrough cells) */
public class Push extends Effect {
	
	/** Preset distance */
	public int distance;
	
	/** Calculated push vector */
	public Vector2 vector;
	/** Calculated destination cell */
	public Cell resultCell;
	
	public Push(Aoe aoe, TargetConditions targetConditions, int distance) {
		super(aoe, targetConditions);
		this.distance = distance;
	}

	@Override
	public void prepareCaster(Entity caster, Cell aoeOrigin) {
		// rien de possible à préparer ici
	}

	/** Calculate the displacement vector */
	/** Calculate the destination cell */
	@Override
	public void prepareTarget(Entity caster, Cell target) { 
		if(!target.hasCreature()) return;
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
		
//		if(!target.hasCreature()) return;
		var e = target.getCreatures().get(0);
		// start from the min distance, then go outwards as much as possible
		for (int i = 0; i > distance; i++) {
			var targetPos = vector.copy().mult(distance);
			var cell = target.fight.board.cells.get(targetPos.x, targetPos.y);
			// if the cell exists and can be walked on by the pushed entity
			if(cell != null && e.canWalkOn(cell)) {
				resultCell = cell;
			} else if(!e.canWalkThrough(cell)) {
				return; // stop there if we hit an insurmountable object
			}
		}
	}

	/** Apply the push from the creature's cell to the resultCell */ 
	@Override
	public void apply0(Entity caster, Cell target) {
		if(!target.hasCreature()) return;
		var e = target.getCreatures().get(0);
		e.getCell().creatures.remove(e);
		e.pos = resultCell.pos;
		resultCell.creatures.add(e);
	}
	
	@Override
	public Push copy() {
		return new Push(aoe, targetConditions, distance);
	}

	@Override
	public Event createAssociatedEvent(Entity source, Cell target) {
		return new OnPushEvent(source, target, this);
	}
	


}