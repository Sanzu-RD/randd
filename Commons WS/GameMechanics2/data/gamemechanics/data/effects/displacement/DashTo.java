package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** Dash to the target cell (stopped by no-passthrough cells) */
public class DashTo extends Effect {

	public DashTo(Aoe aoe, TargetConditionStat targetConditions) {
		super(aoe, targetConditions);
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
		return new DashTo(this.aoe, this.targetConditions);
	}
	
	/*
	public DashTo(int[] areaOfEffect, int targetConditions) {
		super(areaOfEffect, targetConditions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(Entity source, Cell target) {
		//var dist = source.getCell().distanceSquare(target);
		//new Dash(dist).apply(source, target);
	}
	*/
}