package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnLeaveCellEvent extends Event {

	public interface OnLeaveCellHandler extends Handler { //<OnLeaveCellEvent> {
		@Subscribe
		public default void handle0(OnLeaveCellEvent event) {
			if(check(event)) onLeaveCell(event);
		}
		public void onLeaveCell(OnLeaveCellEvent event);
	}
	
	public OnLeaveCellEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnLeaveCellEvent copy0() {
		return new OnLeaveCellEvent(source, target, effect.copy());
	}
	
}
