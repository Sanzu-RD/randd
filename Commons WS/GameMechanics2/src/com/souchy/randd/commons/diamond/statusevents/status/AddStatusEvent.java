package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class AddStatusEvent extends Event {
	
	public interface OnAddStatusHandler extends Handler { //<OnAddStatusEvent> {
		@Subscribe
		public default void handle0(AddStatusEvent event) {
			if(check(event)) onAddStatus(event);
		}
		public void onAddStatus(AddStatusEvent event);
	}
	
	public AddStatusEvent(Creature source, Cell target, AddStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public AddStatusEvent copy0() {
		return new AddStatusEvent(source, target, (AddStatusEffect) effect.copy());
	}

	
}
