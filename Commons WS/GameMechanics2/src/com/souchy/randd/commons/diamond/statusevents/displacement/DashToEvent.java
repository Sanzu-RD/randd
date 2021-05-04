package com.souchy.randd.commons.diamond.statusevents.displacement;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.displacement.DashTo;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class DashToEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnDashToHandler extends Handler { //<OnDashToEvent> {
		@Subscribe
		public default void handle0(DashToEvent event) {
			if(check(event)) onDashTo(event);
		}
		public void onDashTo(DashToEvent e);
	}
	
	
	public DashToEvent(Creature caster, Cell target, DashTo effect) {
		super(caster, target, effect);
	}

	@Override
	public DashToEvent copy0() {
		return new DashToEvent(source, target, (DashTo) effect.copy());
	}
	
}
