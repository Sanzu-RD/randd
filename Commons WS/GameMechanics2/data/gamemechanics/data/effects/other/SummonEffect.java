package gamemechanics.data.effects.other;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class SummonEffect extends Effect {

	public SummonEffect(Aoe aoe, TargetConditionStat targetConditions) {
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

	
//	public SummonEffect(int[] areaOfEffect, int targetConditions) {
//		super(areaOfEffect, targetConditions);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public void apply(Entity caster, Cell target) {
//		// TODO Auto-generated method stub
//		
//	}
	
}
