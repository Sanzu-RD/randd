package gamemechanics.data.effects.status;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditions;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.status.OnModifyStatusEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

/** One-shot effect */
public class ModifyStatusEffect extends Effect {
	
	// mods to add, but to which statuses if not all ? 
	public int modStacks, modDuration;
	
	public ModifyStatusEffect(Aoe aoe, TargetConditions targetConditions, int modStacks, int modDuration) {
		super(aoe, targetConditions);
		this.modStacks = modStacks;
		this.modDuration = modDuration;
	}

	@Override
	public OnModifyStatusEvent createAssociatedEvent(Entity source, Cell target) {
		return new OnModifyStatusEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Entity caster, Cell aoeOrigin) {
		
	}

	@Override
	public void prepareTarget(Entity caster, Cell target) {
		
	}

	@Override
	public void apply0(Entity caster, Cell target) {
		
	}

	@Override
	public ModifyStatusEffect copy() {
		return new ModifyStatusEffect(aoe, targetConditions, modStacks, modDuration);
	}


}