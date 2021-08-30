package com.souchy.randd.commons.diamond.statusevents.displacement;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.displacement.Move;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class MoveStartEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnMoveStartHandler extends Handler { // <OnDashEvent> {
		@Subscribe
		public default void handle0(MoveStartEvent event) {
			if(check(event)) onMoveStart(event);
		}
		public void onMoveStart(MoveStartEvent e);
	}
	
	
	public MoveStartEvent(Creature caster, Cell target, Move effect) {
		super(caster, target, effect);
	}

	@Override
	public MoveStartEvent copy0() {
		return new MoveStartEvent(source, target, (Move) effect.copy());
	}
	
}
