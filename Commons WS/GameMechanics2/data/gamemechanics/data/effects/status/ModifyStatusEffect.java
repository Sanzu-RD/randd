package gamemechanics.data.effects.status;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.status.OnModifyStatusEvent;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;

/** One-shot effect */
public class ModifyStatusEffect extends Effect {
	
	// mods to add, but to which statuses if not all ? 
	public int modStacks, modDuration;
	
	public ModifyStatusEffect(Fight f, Aoe aoe, TargetConditionStat targetConditions, int modStacks, int modDuration) {
		super(f, aoe, targetConditions);
		this.modStacks = modStacks;
		this.modDuration = modDuration;
	}

	@Override
	public OnModifyStatusEvent createAssociatedEvent(Creature source, Cell target) {
		return new OnModifyStatusEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
		
	}

	@Override
	public void prepareTarget(Creature caster, Cell target) {
		
	}

	@Override
	public void apply0(Creature caster, Cell target) {
		
	}

	@Override
	public ModifyStatusEffect copy() {
		return new ModifyStatusEffect(get(Fight.class), aoe, targetConditions, modStacks, modDuration);
	}


}