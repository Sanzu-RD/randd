package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class CastStatusEvent extends Event {
	
	public Status status;

	public CastStatusEvent(Creature source, Cell target, Status status) {
		super(source, target, null);
		this.status = status;
	}
	
	@Override
	public CastStatusEvent copy0() {
		return new CastStatusEvent(source, target, status.copy());
	}
	

	public interface OnCastStatusHandler extends Handler { // <OnEnterCellEvent> {
		@Subscribe
		public default void handle0(CastStatusEvent event) {
			if(check(event)) onCastStatus(event);
		}
		public void onCastStatus(CastStatusEvent event);
	}
	
	
}
