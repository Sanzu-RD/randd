package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Pull the first creature in line to the target cell (stopped by no-passthrough cells) */
public class PullTo extends Effect {

	public PullTo(Aoe aoe, TargetConditionStat targetConditions) {
		super(aoe, targetConditions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Event createAssociatedEvent(Entity source, Cell target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareCaster(Entity caster, Cell aoeOrigin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareTarget(Entity caster, Cell target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply0(Entity caster, Cell target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Effect copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public PullTo(int[] areaOfEffect, int targetConditions) {
//		super(areaOfEffect, targetConditions);
//	}
//
//	@Override
//	public void apply(Entity source, Cell target) {
//		// from the source to the target, iterate cells and find the first creature.
//		// then try to pull the creature to the target cell until there is an object blocking the way
//	}
}