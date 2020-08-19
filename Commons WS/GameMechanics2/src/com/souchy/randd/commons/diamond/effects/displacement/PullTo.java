package com.souchy.randd.commons.diamond.effects.displacement;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.TargetConditionStat;
import com.souchy.randd.commons.diamond.statusevents.Event;

/** Pull the first creature in line to the target cell (stopped by no-passthrough cells) */
public class PullTo extends Effect {

	public PullTo(Fight f, Aoe aoe, TargetConditionStat targetConditions) {
		super(f, aoe, targetConditions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareTarget(Creature caster, Cell target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply0(Creature caster, Cell target) {
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