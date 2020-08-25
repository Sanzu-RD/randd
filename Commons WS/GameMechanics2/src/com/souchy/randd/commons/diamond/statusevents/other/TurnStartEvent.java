package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class TurnStartEvent extends Event {

	public interface OnTurnStartHandler extends Handler { //<OnTurnStartEvent> {
		@Subscribe
		public default void handle0(TurnStartEvent event) {
			if(check(event)) onTurnStart(event);
		}
		public void onTurnStart(TurnStartEvent event);
	}
	
	public TurnStartEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public TurnStartEvent copy0() {
		return new TurnStartEvent(source, target, effect.copy());
	}
	
}
