package com.souchy.randd.commons.diamond.effects.displacement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.Pathfinding;
import com.souchy.randd.commons.diamond.common.Pathfinding.Node;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.stats.special.TargetTypeStat;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.displacement.MoveStartEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.MoveStartEvent.OnMoveStartHandler;
import com.souchy.randd.commons.diamond.statusevents.displacement.MoveStopEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.WalkEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.LeaveCellEvent;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class Move extends Effect implements Reactor, OnMoveStartHandler, OnEnterCellHandler {
	
	public List<Node> path = new ArrayList<>();
	private AtomicInteger current = new AtomicInteger(0);

	public Move(Aoe aoe, TargetTypeStat targetConditions) {
		super(aoe, targetConditions);
	}

	@Override
	public Event createAssociatedEvent(Creature source, Cell target) {
//		var f = source.get(Fight.class);
//		return new WalkEvent(source, f.board.get(path.get(0).pos), this);
		return new WalkEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Creature caster, Cell target) {
		if(this.path == null) 
			this.path = Pathfinding.aStar(caster.get(Fight.class).board, caster, caster.getCell(), target);
	}

	@Override
	public void prepareTarget(Creature caster, Cell target) {
		
	}

	@Override
	public void apply0(Creature caster, Cell target) {
		if(path.size() == 0) {
			Log.info("Move Effect path size = 0");
			return;
		}
		// start with WalkEvent ? 
		
		// while not interrupted 
		// step on every cell 1 at a time
		// create an event for each cell -> EnterCellEvent, LeaveCellEvent
		
		
		var f = caster.get(Fight.class);
		var i = current.getAndIncrement();
		
		if(i == 0) 
			caster.get(Fight.class).statusbus.post(new MoveStartEvent(caster, target, this));
		

		f.statusbus.post(new LeaveCellEvent(caster, f.board.get(caster.pos), this));
		caster.pos.set(path.get(i).pos);
		//path.remove(0);
		f.statusbus.post(new EnterCellEvent(caster, f.board.get(caster.pos), this));
		
		if(i < path.size() - 1) 
			this.apply(caster, target);
		
		if(i == path.size() - 1)
			caster.get(Fight.class).statusbus.post(new MoveStopEvent(caster, target, this));
	}

	@Override
	public Effect copy() {
		return new Move(this.aoe, this.targetConditions);
	}

	@Override
	public void onMoveStart(MoveStartEvent e) {
		//caster.pos.set(finalpos);
	}
	
	@Override
	public void onEnterCell(EnterCellEvent event) {
		// TODO Auto-generated method stub
	}

	
}
