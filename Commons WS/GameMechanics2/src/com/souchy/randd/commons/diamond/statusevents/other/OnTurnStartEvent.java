package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnTurnStartEvent extends Event {

	public interface OnTurnStartHandler extends Handler { //<OnTurnStartEvent> {
		@Subscribe
		public default void handle0(OnTurnStartEvent event) {
			if(check(event)) onTurnStart(event);
		}
		public void onTurnStart(OnTurnStartEvent event);
	}
	
	public OnTurnStartEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnTurnStartEvent copy0() {
		return new OnTurnStartEvent(source, target, effect.copy());
	}
	
}
