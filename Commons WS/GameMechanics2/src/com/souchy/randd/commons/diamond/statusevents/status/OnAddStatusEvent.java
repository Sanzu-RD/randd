package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnAddStatusEvent extends Event {
	
	public interface OnAddStatusHandler extends Handler { //<OnAddStatusEvent> {
		@Subscribe
		public default void handle0(OnAddStatusEvent event) {
			if(check(event)) onAddStatus(event);
		}
		public void onAddStatus(OnAddStatusEvent event);
	}
	
	public OnAddStatusEvent(Creature source, Cell target, AddStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnAddStatusEvent copy0() {
		return new OnAddStatusEvent(source, target, (AddStatusEffect) effect.copy());
	}

	
}
