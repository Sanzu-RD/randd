package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.RemoveStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnRemoveStatusEvent extends Event {

	public interface OnRemoveStatusHandler extends Handler { //<OnRemoveStatusEvent> {
		@Subscribe
		public default void handle0(OnRemoveStatusEvent event) {
			if(check(event)) onRemoveStatus(event);
		}
		public void onRemoveStatus(OnRemoveStatusEvent event);
	}
	
	public OnRemoveStatusEvent(Creature source, Cell target, RemoveStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnRemoveStatusEvent copy0() {
		return new OnRemoveStatusEvent(source, target, (RemoveStatusEffect) effect.copy());
	}
	
}
