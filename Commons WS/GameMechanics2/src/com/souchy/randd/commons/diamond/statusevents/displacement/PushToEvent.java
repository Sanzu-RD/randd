package com.souchy.randd.commons.diamond.statusevents.displacement;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.displacement.PushTo;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class PushToEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnPushToHandler extends Handler { //<OnPushToEvent> {
		@Subscribe
		public default void handle0(PushToEvent event) {
			if(check(event)) onPushTo(event);
		}
		public void onPushTo(PushToEvent e);
	}
	
	
	public PushToEvent(Creature caster, Cell target, PushTo effect) {
		super(caster, target, effect);
	}

	@Override
	public PushToEvent copy0() {
		return new PushToEvent(source, target, (PushTo) effect.copy());
	}
	
}
