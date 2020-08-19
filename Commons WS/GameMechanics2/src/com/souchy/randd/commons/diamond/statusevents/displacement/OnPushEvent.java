package com.souchy.randd.commons.diamond.statusevents.displacement;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.displacement.Push;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnPushEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnPushHandler extends Handler { // <OnPushEvent> {
		@Subscribe
		public default void handle0(OnPushEvent event) {
			if(check(event)) onPush(event);
		}
		public void onPush(OnPushEvent e);
	}
	
	
	public OnPushEvent(Creature caster, Cell target, Push effect) {
		super(caster, target, effect);
	}

	@Override
	public OnPushEvent copy0() {
		return new OnPushEvent(source, target, (Push) effect.copy());
	}
	
}
