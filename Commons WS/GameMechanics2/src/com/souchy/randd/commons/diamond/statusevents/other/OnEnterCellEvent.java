package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnEnterCellEvent extends Event {

	public interface OnEnterCellHandler extends Handler { // <OnEnterCellEvent> {
		@Subscribe
		public default void handle0(OnEnterCellEvent event) {
			if(check(event)) onEnterCell(event);
		}
		public void onEnterCell(OnEnterCellEvent event);
	}
	
	public OnEnterCellEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnEnterCellEvent copy0() {
		return new OnEnterCellEvent(source, target, effect.copy());
	}
	
}
