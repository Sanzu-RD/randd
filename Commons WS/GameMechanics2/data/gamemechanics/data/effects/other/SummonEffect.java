package gamemechanics.data.effects.other;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;

public class SummonEffect extends Effect {

	public SummonEffect(Fight f, Aoe aoe, TargetConditionStat targetConditions) {
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
