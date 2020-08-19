package com.souchy.randd.commons.diamond.effects.displacement;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.TargetConditionStat;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statusevents.Event;

/** Dashes a set distance away from target cell (stopped by no-passthrough cells) */
public class DashFrom extends Effect {

	public IntStat distance;
	
	public DashFrom(Fight f, Aoe aoe, TargetConditionStat targetConditions, int distance) {
		super(f, aoe, targetConditions);
		this.distance = new IntStat(distance);
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
	public DashFrom copy() {
		var effect = new DashFrom(get(Fight.class), aoe, targetConditions, 0);
		effect.distance = this.distance.copy();
		return effect;
	}
	
	
	/*
	public int distance;
	
	public DashFrom(int[] areaOfEffect, int targetConditions, int distance) {
		super(areaOfEffect, targetConditions);
		this.distance = distance;
	}
	
	@Override
	public void apply(Entity source, Cell target) {
		var p1 = source.getCell().pos;
		var p2 = target.pos;
		
		//var d = p2.copy().sub(p1).abs(); // delta en absolu
	}
	 */

}
