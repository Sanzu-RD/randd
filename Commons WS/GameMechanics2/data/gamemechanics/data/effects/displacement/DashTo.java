package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;

/** Dash to the target cell (stopped by no-passthrough cells) */
public class DashTo extends Effect {

	public DashTo(Fight f, Aoe aoe, TargetConditionStat targetConditions) {
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
		return new DashTo(get(Fight.class), this.aoe, this.targetConditions);
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