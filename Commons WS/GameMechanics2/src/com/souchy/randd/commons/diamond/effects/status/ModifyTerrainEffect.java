package com.souchy.randd.commons.diamond.effects.status;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.status.ModifyStatusEvent;
import com.souchy.randd.commons.diamond.statusevents.status.ModifyTerrainEvent;

/** One-shot effect */
public class ModifyTerrainEffect extends Effect {
	
	// mods to add, but to which statuses if not all ? 
	public int modStacks, modDuration;
	public Status status;
	
	public ModifyTerrainEffect(Aoe aoe, TargetTypeStat targetConditions, Status target, int modStacks, int modDuration) {
		super(aoe, targetConditions);
		this.modStacks = modStacks;
		this.modDuration = modDuration;
		this.status = target;
	}

	@Override
	public ModifyTerrainEvent createAssociatedEvent(Creature source, Cell target) {
		return new ModifyTerrainEvent(source, target, this);
	}

	@Override
	public void apply0(Creature caster, Cell cell) {
		this.status.duration = modDuration;
		this.status.stacks = modStacks;
	}

	@Override
	public ModifyTerrainEffect copy() {
		return new ModifyTerrainEffect(aoe, targetConditions, status, modStacks, modDuration);
	}


}