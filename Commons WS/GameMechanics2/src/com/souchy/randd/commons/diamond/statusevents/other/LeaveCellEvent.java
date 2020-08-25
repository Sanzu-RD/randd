package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class LeaveCellEvent extends Event {

	public interface OnLeaveCellHandler extends Handler { //<OnLeaveCellEvent> {
		@Subscribe
		public default void handle0(LeaveCellEvent event) {
			if(check(event)) onLeaveCell(event);
		}
		public void onLeaveCell(LeaveCellEvent event);
	}
	
	public LeaveCellEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public LeaveCellEvent copy0() {
		return new LeaveCellEvent(source, target, effect.copy());
	}
	
}
