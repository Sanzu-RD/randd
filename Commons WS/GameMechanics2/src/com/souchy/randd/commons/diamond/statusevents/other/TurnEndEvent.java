package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class TurnEndEvent extends Event {

	public interface OnTurnEndHandler extends Handler { //<OnTurnEndEvent> {
		@Subscribe
		public default void handle0(TurnEndEvent event) {
			if(check(event)) onTurnEnd(event);
		}
		public void onTurnEnd(TurnEndEvent event);
	}
	
	public TurnEndEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public TurnEndEvent copy0() {
		return new TurnEndEvent(source, target, effect.copy());
	}
	
}
