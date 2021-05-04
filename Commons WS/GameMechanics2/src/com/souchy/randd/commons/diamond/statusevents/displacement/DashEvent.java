package com.souchy.randd.commons.diamond.statusevents.displacement;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.displacement.Dash;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class DashEvent extends Event {
	
	/** interface for statuses to implement */
	public interface OnDashHandler extends Handler { // <OnDashEvent> {
		@Subscribe
		public default void handle0(DashEvent event) {
			if(check(event)) onDash(event);
		}
		public void onDash(DashEvent e);
	}
	
	
	public DashEvent(Creature caster, Cell target, Dash effect) {
		super(caster, target, effect);
	}

	@Override
	public DashEvent copy0() {
		return new DashEvent(source, target, (Dash) effect.copy());
	}
	
}
