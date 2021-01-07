package com.souchy.randd.commons.diamond.statusevents.resource;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class ResourceGainLossEvent  extends Event {

	public interface OnResourcseUseHandler extends Handler { //<OnAddStatusEvent> {
		@Subscribe
		public default void handle0(ResourceGainLossEvent event) {
			if(check(event)) onResourceGainLoss(event);
		}
		public void onResourceGainLoss(ResourceGainLossEvent event);
	}
	
	public ResourceGainLossEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}

	@Override
	public Event copy0() {
		return new ResourceUseEvent(source, target, effect);
	}
	
}
