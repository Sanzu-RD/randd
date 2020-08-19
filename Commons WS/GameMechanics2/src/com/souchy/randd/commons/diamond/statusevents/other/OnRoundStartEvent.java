package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnRoundStartEvent extends Event {

	public interface OnRoundStartHandler extends Handler { //<OnRoundStartEvent> {
		@Subscribe
		public default void handle0(OnRoundStartEvent event) {
			if(check(event)) onRoundStart(event);
		}
		public void onRoundStart(OnRoundStartEvent event);
	}
	
	public OnRoundStartEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnRoundStartEvent copy0() {
		return new OnRoundStartEvent(source, target, effect.copy());
	}
	
}
