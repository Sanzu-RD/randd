package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class RoundStartEvent extends Event {

	public interface OnRoundStartHandler extends Handler { //<OnRoundStartEvent> {
		@Subscribe
		public default void handle0(RoundStartEvent event) {
			if(check(event)) onRoundStart(event);
		}
		public void onRoundStart(RoundStartEvent event);
	}
	
	public RoundStartEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public RoundStartEvent copy0() {
		return new RoundStartEvent(source, target, effect.copy());
	}
	
}
