package gamemechanics.data.effects.displacement;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;

/** Pushes the first creature in line with the source to the target cell (stopped by no-passthrough cells) */
public class PushTo extends Effect {

	public PushTo(Fight f, Aoe aoe, TargetConditionStat targetConditions) {
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
	
//	public PushTo(int[] areaOfEffect, int targetConditions) {
//		super(areaOfEffect, targetConditions);
//	}
//
//	@Override
//	public void apply(Entity source, Cell target) {
//		// from the source to the target, iterate cells and find the first creature.
//		// then try to push the creature to the target cell until there is an object blocking the way
//	}
}