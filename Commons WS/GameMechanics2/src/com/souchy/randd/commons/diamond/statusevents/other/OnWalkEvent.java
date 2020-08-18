package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnWalkEvent extends Event {

	public interface OnWalkHandler extends Handler { //<OnWalkEvent> {
		@Subscribe
		public default void handle0(OnWalkEvent event) {
			if(check(event)) onWalk(event);
		}
		public void onWalk(OnWalkEvent event);
	}
	
	public OnWalkEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnWalkEvent copy0() {
		return new OnWalkEvent(source, target, effect.copy());
	}
}
