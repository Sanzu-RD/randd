package com.souchy.randd.commons.diamond.effects.displacement;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.Pathfinding.Node;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.Event;

public class Move extends Effect {
	
	public List<Node> path = new ArrayList<>();

	public Move(Fight f, Aoe aoe, TargetTypeStat targetConditions) {
		super(f, aoe, targetConditions);
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
		return null;
	}

	@Override
	public void prepareCaster(Creature caster, Cell target) {
		this.path = Pathfinding.aStar(get(Fight.class).board, caster, caster.getCell(), target);
	}

	@Override
	public void prepareTarget(Creature caster, Cell target) {
		
	}

	@Override
	public void apply0(Creature caster, Cell target) {
		// start with WalkEvent ? 
		
		// while not interrupted 
		// step on every cell 1 at a time
		// create an event for each cell -> EnterCellEvent, LeaveCellEvent
	}

	@Override
	public Effect copy() {
		return new Move(get(Fight.class), this.aoe, this.targetConditions);
	}
	
}
