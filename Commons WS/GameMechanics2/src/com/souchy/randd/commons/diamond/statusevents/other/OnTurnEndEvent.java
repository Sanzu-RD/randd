package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnTurnEndEvent extends Event {

	public interface OnTurnEndHandler extends Handler { //<OnTurnEndEvent> {
		@Subscribe
		public default void handle0(OnTurnEndEvent event) {
			if(check(event)) onTurnEnd(event);
		}
		public void onTurnEnd(OnTurnEndEvent event);
	}
	
	public OnTurnEndEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnTurnEndEvent copy0() {
		return new OnTurnEndEvent(source, target, effect.copy());
	}
	
}
