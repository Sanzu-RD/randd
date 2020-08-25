package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class EnterCellEvent extends Event {

	public interface OnEnterCellHandler extends Handler { // <OnEnterCellEvent> {
		@Subscribe
		public default void handle0(EnterCellEvent event) {
			if(check(event)) onEnterCell(event);
		}
		public void onEnterCell(EnterCellEvent event);
	}
	
	public EnterCellEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public EnterCellEvent copy0() {
		return new EnterCellEvent(source, target, effect.copy());
	}
	
}
