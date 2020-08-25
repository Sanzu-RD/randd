package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.ModifyStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class ModifyStatusEvent extends Event {

	public interface OnModifyStatusHandler extends Handler { //<OnModifyStatusEvent> {
		@Subscribe
		public default void handle0(ModifyStatusEvent event) {
			if(check(event)) onModifyStatus(event);
		}
		public void onModifyStatus(ModifyStatusEvent event);
	}
	
	public ModifyStatusEvent(Creature source, Cell target, ModifyStatusEffect effect) {
		super(source, target, effect);
	}

	@Override
	public ModifyStatusEvent copy0() {
		return new ModifyStatusEvent(source, target, (ModifyStatusEffect) effect.copy());
	}
	
}
