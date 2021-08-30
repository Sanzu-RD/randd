package com.souchy.randd.commons.diamond.statusevents.displacement;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.displacement.Dash;
import com.souchy.randd.commons.diamond.effects.displacement.Move;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class MoveStopEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnMoveStopHandler extends Handler { // <OnDashEvent> {
		@Subscribe
		public default void handle0(MoveStopEvent event) {
			if(check(event)) onMoveStop(event);
		}
		public void onMoveStop(MoveStopEvent e);
	}
	
	
	public MoveStopEvent(Creature caster, Cell target, Move effect) {
		super(caster, target, effect);
	}

	@Override
	public MoveStopEvent copy0() {
		return new MoveStopEvent(source, target, (Move) effect.copy());
	}
	
}
