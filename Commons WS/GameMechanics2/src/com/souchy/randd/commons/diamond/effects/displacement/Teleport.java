package com.souchy.randd.commons.diamond.effects.displacement;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.special.TargetConditionStat;
import com.souchy.randd.commons.diamond.statusevents.Event;

/** Teleport to the target cell */
public class Teleport extends Effect {

	public Teleport(Fight f, Aoe aoe, TargetConditionStat targetConditions) {
		super(f, aoe, targetConditions);
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
	
//	public Teleport(int[] areaOfEffect, int targetConditions) {
//		super(areaOfEffect, targetConditions);
//	}
//
//	@Override
//	public void apply(Entity source, Cell target) {
//		// teleport to target cell
//	}
	
}