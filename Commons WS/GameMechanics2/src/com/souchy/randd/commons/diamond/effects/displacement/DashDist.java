package com.souchy.randd.commons.diamond.effects.displacement;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.special.TargetConditionStat;
import com.souchy.randd.commons.diamond.statusevents.Event;

/** Dashes a set distance in the direction of the target cell (stopped by no-passthrough cells) */
public class DashDist extends Effect {

	public IntStat distance;
	
	public DashDist(Fight f, Aoe aoe, TargetConditionStat targetConditions, int distance) {
		super(f, aoe, targetConditions);
		this.distance = new IntStat(distance);
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
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
	public DashDist copy() {
		var effect = new DashDist(get(Fight.class), aoe, targetConditions, 0);
		effect.distance = this.distance.copy();
		return effect;
	}
	
//	public DashDist(int[] areaOfEffect, int targetConditions, int dist) {
//		super(areaOfEffect, targetConditions);
//		this.distance = dist;
//	}
//
//	public int distance;
//	
//	@Override
//	public void apply(Entity source, Cell target) {
//		var p1 = source.getCell().pos;
//		var p2 = target.pos;
//		
//		var d = p2.copy().sub(p1).abs(); // delta en absolu
//		
//	}
	
}
