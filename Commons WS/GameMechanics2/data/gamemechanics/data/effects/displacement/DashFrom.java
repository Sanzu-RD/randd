package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import data.new1.spellstats.base.IntStat;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;

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
