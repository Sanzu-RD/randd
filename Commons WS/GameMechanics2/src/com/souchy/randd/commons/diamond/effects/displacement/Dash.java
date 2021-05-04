package com.souchy.randd.commons.diamond.effects.displacement;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.generic.Vector2;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.base.BoolStat;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.models.stats.special.Targetting;
import com.souchy.randd.commons.diamond.statics.properties.Orientation;
import com.souchy.randd.commons.diamond.statics.properties.Targetability;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.displacement.DashEvent;

/** 
 * Dashes should always be in a line Im pretty sure
 * 
 * Dashes a set distance in the direction of the target cell (stopped by no-passthrough cells) 
 */
public class Dash extends Effect {

	public IntStat distance;
	public BoolStat allOrNothing = new BoolStat(false);
	public Targetting targetting = new Targetting()
			.setBase(Targetability.CanWalkThroughHole, true)
			.setBase(Targetability.CanWalkThroughWall, true);

	

	private Vector2 finalpos;
	
	public Dash(Aoe aoe, TargetTypeStat targetConditions, int distance) {
		super(aoe, targetConditions);
		this.distance = new IntStat(distance);
	}
	
	@Override
	public void prepareCaster(Creature caster, Cell aoeOrigin) {
	}

	@Override
	public void prepareTarget(Creature caster, Cell target) {
		finalpos = Pathfinding.dashFunction(caster, target, distance.value(), allOrNothing.value(), targetting);
	}

	@Override
	public void apply0(Creature caster, Cell target) {
		caster.pos.set(finalpos);
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return new DashEvent(source, target, this);
	}
	
	@Override
	public Dash copy() {
		var effect = new Dash(aoe, targetConditions, 0);
		effect.distance = this.distance.copy();
		return effect;
	}
	
	
}
