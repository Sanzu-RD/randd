package com.souchy.randd.commons.diamond.statusevents.resource;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class ResourceUseEvent extends ResourceGainLossEvent {

	public interface OnResourceUseHandler extends Handler { //<OnAddStatusEvent> {
		@Subscribe
		public default void handle0(ResourceUseEvent event) {
			if(check(event)) onResourceUse(event);
		}
		public void onResourceUse(ResourceUseEvent event);
	}
	
	public ResourceUseEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}

	@Override
	public Event copy0() {
		return new ResourceUseEvent(source, target, effect);
	}
	
}
