package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.ModifyStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class OnModifyStatusEvent extends Event {

	public interface OnModifyStatusHandler extends Handler { //<OnModifyStatusEvent> {
		@Subscribe
		public default void handle0(OnModifyStatusEvent event) {
			if(check(event)) onModifyStatus(event);
		}
		public void onModifyStatus(OnModifyStatusEvent event);
	}
	
	public OnModifyStatusEvent(Creature source, Cell target, ModifyStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public OnModifyStatusEvent copy0() {
		return new OnModifyStatusEvent(source, target, (ModifyStatusEffect) effect.copy());
	}
	
}
