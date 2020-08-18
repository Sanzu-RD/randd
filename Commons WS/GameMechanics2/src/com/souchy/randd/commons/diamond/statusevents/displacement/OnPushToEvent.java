package com.souchy.randd.commons.diamond.statusevents.displacement;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.displacement.PushTo;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnPushToEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnPushToHandler extends Handler { //<OnPushToEvent> {
		@Subscribe
		public default void handle0(OnPushToEvent event) {
			if(check(event)) onPushTo(event);
		}
		public void onPushTo(OnPushToEvent e);
	}
	
	
	public OnPushToEvent(Creature caster, Cell target, PushTo effect) {
		super(caster, target, effect);
	}

	@Override
	public OnPushToEvent copy0() {
		return new OnPushToEvent(source, target, (PushTo) effect.copy());
	}
	
}
