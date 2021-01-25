package com.souchy.randd.commons.diamond.effects.status;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.status.ModifyStatusEvent;

/** One-shot effect */
public class ModifyStatusEffect extends Effect {
	
	// mods to add, but to which statuses if not all ? 
	public int modStacks, modDuration;
	public  Status target;
	
	public ModifyStatusEffect(Fight f, Aoe aoe, TargetTypeStat targetConditions, Status target, int modStacks, int modDuration) {
		super(f, aoe, targetConditions);
		this.modStacks = modStacks;
		this.modDuration = modDuration;
		this.target = target;
	}

	@Override
	public ModifyStatusEvent createAssociatedEvent(Creature source, Cell target) {
		return new ModifyStatusEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
		
	}

	@Override
	public void prepareTarget(Creature caster, Cell cell) {
		
	}

	@Override
	public void apply0(Creature caster, Cell cell) {
		this.target.duration += modDuration;
		this.target.stacks += modStacks;
	}

	@Override
	public ModifyStatusEffect copy() {
		return new ModifyStatusEffect(get(Fight.class), aoe, targetConditions, target, modStacks, modDuration);
	}


}