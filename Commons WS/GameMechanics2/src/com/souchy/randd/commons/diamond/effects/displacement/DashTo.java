package com.souchy.randd.commons.diamond.effects.displacement;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.stats.base.BoolStat;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.models.stats.special.Targetting;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.displacement.DashToEvent;

/** Dash to the target cell (stopped by no-passthrough cells) */
public class DashTo extends Effect {

	public BoolStat allOrNothing = new BoolStat(false);
	public Targetting targetting = new Targetting()
			.setBase(Targetability.CanWalkThroughHole, true)
			.setBase(Targetability.CanWalkThroughWall, true);
	

	private Vector2 finalpos;
	
	public DashTo(Aoe aoe, TargetTypeStat targetConditions) {
		super(aoe, targetConditions);
	}

	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
	}

	@Override
	public void prepareTarget(Creature caster, Cell target) {
		finalpos = Pathfinding.dashFunction(caster, target, 0, allOrNothing.value(), targetting);
	}

	@Override
	public void apply0(Creature caster, Cell target) {
		if(target.pos.isEqual(finalpos))
			caster.pos.set(finalpos);
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new DashToEvent(source, target, this);
	}

	@Override
	public Effect copy() {
		return new DashTo(this.aoe, this.targetConditions);
	}


}