package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class RoundEndEvent extends Event {

	public interface OnRoundEndHandler extends Handler { //<OnRoundEndEvent> {
		@Subscribe
		public default void handle0(RoundEndEvent event) {
			if(check(event)) onRoundEnd(event);
		}
		public void onRoundEnd(RoundEndEvent event);
	}
	
	public RoundEndEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public RoundEndEvent copy0() {
		return new RoundEndEvent(source, target, effect.copy());
	}
	
}
