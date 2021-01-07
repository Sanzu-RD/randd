package com.souchy.randd.commons.diamond.effects.status;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.status.RemoveGlyphEvent;

public class RemoveGlyphEffect extends Effect {

	public RemoveGlyphEffect(Fight f, Aoe aoe, TargetTypeStat targetConditions) {
		super(f, aoe, targetConditions);
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new RemoveGlyphEvent(source, target, this);
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
	public Effect copy() {
		return new RemoveGlyphEffect(get(Fight.class), aoe, targetConditions);
	}
	
}
