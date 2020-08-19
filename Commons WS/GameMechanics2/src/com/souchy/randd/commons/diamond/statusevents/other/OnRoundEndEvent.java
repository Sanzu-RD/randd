package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnRoundEndEvent extends Event {

	public interface OnRoundEndHandler extends Handler { //<OnRoundEndEvent> {
		@Subscribe
		public default void handle0(OnRoundEndEvent event) {
			if(check(event)) onRoundEnd(event);
		}
		public void onRoundEnd(OnRoundEndEvent event);
	}
	
	public OnRoundEndEvent(Creature source, Cell target, Effect effect) {
		super(source, target, effect);
	}
	
	@Override
	public OnRoundEndEvent copy0() {
		return new OnRoundEndEvent(source, target, effect.copy());
	}
	
}
